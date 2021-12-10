######################################################################
# Extremely rough piecemeal conversion of my old Java scale code
# into Python, just for my personal use.
# Uses the unprocessed LaTeX file for the scale book as a data source.
#
# (C) Rich Cochrane 2020. All rights reserved.
# http://cochranemusic.com
#####################################################################

EDO = 12
pc = list(range(0, EDO))
names = ["C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"]
inames = ["s", "t", "mT", "MT", "Fo", "Tr", "Fi"]
flat_nums = ["1", "b2", "2", "b3", "3", "4", "b5", "5", "b6", "6", "b7", "7"]
sharp_nums = ["1", "#1", "2", "#2", "3", "4", "#4", "5", "#5", "6", "#6", "7"]

names12EDO = ["C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"]
names18EDO = ["C", "C3t", "D3b", "D", "D3t", "E3b", "E", "E3t", "F3t", "F#", "G3b", "G3t", "G#", "A3b", "A3t", "A#", "B3b", "B3t"]
names22EDO = [str(i) for i in range(22)]
names24EDO = ["C", "Ct", "C#", "Dd", "D", "Dt", "D#", "Ed", "E", "Et", "F", "Ft", "F#", "Gd", "G", "Gt", "G#", "Ad", "A", "At", "A#", "Bd", "B", "Bt"]

scales = {}

def gen_note_names(edo):
  return [str(i) for i in range(edo)]

def add(pc, interval):
  return (pc + interval) % EDO

def transpose(scale, interval):
  return [add(s, interval) for s in scale]

def merge(sc1, sc2):
  res = sc1.copy()
  res.extend(sc2)
  return sortScale(res)


def sortScale(scale):
  return sorted(list(set(scale)))

def getAllScalesBySize(size):
  ret = []
  for i in range(2**EDO):
    b = "{0:b}".format(i)
    if b.count("1") == size:
      ret.append(binaryToPCList(b))
  return ret

def getModes(scale):
  modes = []
  for i in range(len(scale)):
    x = scale[i:]
    x.extend(scale[:i])
    x = transpose(x, -1 * x[0])
    if x[0] == 0:
      modes.append(x)
  return modes

def equalByRotation(scale1, scale2):
  if len(scale1) != len(scale2):
    return False
  for i in range(len(scale1)):
    x = scale1[:i]
    x.extend(scale1[i:])
    if x == scale2:
      return True
  return False

def equalByTransposition(scale1, scale2):
  if len(scale1) != len(scale2):
    return False
  for i in range(EDO):
    x = [add(s, i) for s in scale1]
    if x == scale2:
      return True
  return False

def equivalent(scale1, scale2):
  if len(scale1) != len(scale2):
    return False
  for i in range(len(scale1)):
    x = scale1[i:]
    x.extend(scale1[:i])
    if equalByTransposition(x, scale2):
      return True
  return False

def toNoteNames(scale):
  return [names[s] for s in scale]

def toIMap(scale):
  imap = []
  prev = 12 - scale[-1]
  for s in scale:
    imap.append(abs(s - prev) - 1)
    prev = s
  ret = imap[1:]
  ret.append(imap[0])
  return ret

def fromIMap(imap):
  s = [0]
  for i in imap:
    n = add(s[-1], i)
    s.append(n)
  s = sorted(list(set(s)))
  return s

def toINames(scale):
  return [inames[s] for s in toIMap(scale)]

def coscale(scale):
  return [i for i in range(EDO) if i not in scale]

def isSubset(small, large):
  if len(small) >= len(large):
    return False
  for i in range(12):
    if set(sortScale(transpose(small, 12-small[0]))).issubset(set(large)):
      return True
  return False

def harmonizeInThirds(scale, num=3):
  results = []
  for count, value in enumerate(scale):
    chord = []
    for i in range(0, 2*num - 1, 2):
      chord.append(scale[(count + i) % 7])
    chord = sortScale(chord)
    chord = transpose(chord, -1* chord[0])
    results.append(chord)
  return results

def getDoubleCombinations(scale):
  return getCombinations(scale, scale)

def getCombinations(sc1, sc2):
  res = []
  for i in range(1, 12):
    s = sc2.copy()
    s = transpose(s, i)
    s.extend(sc1)
    s = sortScale(s)
    res.append({"Notes": s, "Transpose": i})
  return res

def printListOfLists(lol):
  for l in lol:
    print(l)

def printListOfStr(los):
  res = "["
  for s in los:
    res = res + s + ", "
  res = res[:-2] + "]"
  print(res)

def getScaleFromPCList(pclist):
  for i in range(0, 12):
    cp = sortScale(transpose(pclist, i))
    if 0 in cp:
      for c in scales.items():
        if cp == c[1]["Notes"]:
          return {"Name": c[0], "Details": c[1], "Transpose": i}
  return({"Name": "No matching scale for " + str(pclist) , "Details": None, "Transpose": 0} )

def binaryToPCList(bin):
  pcs = []
  for i, b in enumerate(bin):
    if b == "1":
      pcs.append(i)
  return pcs

def getScale(name):
  name =  simplifyForMatching(name)
  for s in scales.items():
    if  simplifyForMatching(s[0]) == name:
      return {"Name": s[0], "Details": s[1], "Transpose": 0}
    else:
      mi = 0
      for m in s[1]["ModeNames"]:
        if  simplifyForMatching(m) == name:
          t = sortScale(transpose(s[1]["Notes"], s[1]["Notes"][mi]))
          return {"Name": s[0], "Details": s[1], "Mode": t}
        mi+=1

def invert(scale):
  res = [0]
  s = scale.copy()
  s = s[::-1]
  s.insert(0, 12)
  for i in range(1, len(s)):
    n = add(res[-1], (s[i-1] - s[i]))
    res.append(n)
  return sortScale(res)

def simplifyForMatching(s):
  return s.lower().replace(" ", "")

def getAllNNotePCSets(n):
  scaleList = []
  for i in range(0, 2**12):
    s = "{0:b}".format(i)
    if s.count("1") == n:
      sc = binaryToPCList(s)
      scaleList.append(sc)
  return scaleList

def isModeInList(scale, scaleList):
  for s in scaleList:
    if equivalent(scale, s):
      return True
  return False

def findIdxOfModeInList(scale, scaleList):
  for i, s in enumerate(scaleList):
    if equivalent(scale, s):
      return i
  return -1


def writeScalaFile(scale, name, desc="A tuning", folder=""):
  fname = name + ".scl"
  if folder != "":
    fname = folder + "/" + fname
  fname = "scala/" + fname
  f = open(fname, "w")
  f.write("!\n!\n")
  f.write(desc + "\n")
  f.write(" " + str(len(scale)) + "\n")
  f.write("!\n")
  curr = 0
  incr = 1200 / EDO
  for n in scale[1:]:
    f.write(" " + str(incr * n) + "\n")
    #print("     ", n, incr * n)
  f.write(" 2/1\n")
  f.close()

#####################################################################
# Scrape all scale details from the old guitar book TeX file.
# This is pretty messy but it works.
#####################################################################
initialized = False

noteNameToPC = {
  "1": 0, "b2": 1, "#1": 1, 
  "2": 2, "bb3": 2, 
  "#2": 3, "b3": 3, "bb4": 3, 
  "##2": 4, "3": 4, "b4": 4, 
  "#3": 5, "4": 5, "#3": 5, "bb5": 5,
  "##3": 6, "#4": 6, "b5": 6,
  "##4": 7, "5": 7, "bb6": 7,
  "#5": 8, "b6": 8,
  "##5": 9, "6": 9, "bb7":9,
  "#6": 10, "b7": 10,
  "7": 11, "##6": 7
}

def init():
  global initialized
  global scales
  global noteNameToPC
  if initialized:
    return
  f = open("gchords_experiment.tex")
  s = ""
  for l in f:
    s += l
  f.close()
  arr = s.split("%")[1::2]
  for a in arr:
    if a[:2] == "~M":
      a = a.split("|")
      scl = {}
      n = []
      notes = a[2].split(",")
      for x in notes:
        x = x.replace(" ", "")
        if x in noteNameToPC.keys():
          n.append(noteNameToPC[x])
        else:
          print("Error decoding notes: ", x, notes)
          n.append("X")
      scl["Notes"] = sortScale(n)
      mn = []
      modenames = a[3].split("@")
      for m in modenames:
        if "`" in m:
          m = m.split("`")[0]
        m=m.replace ("\n", "")
        m=m.replace ("\\", "")
        mn.append(m)
      scl["ModeNames"] = mn
      scales[mn[0]] = scl
  initialized = True

def dumpScales():
  global scales
  print(scales)
