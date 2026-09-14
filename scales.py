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
inames = ["s", "t", "mT", "MT", "Fo", "Tr", "Fi", "m6", "M6", "m7", "M7"]
inames10EDO = ["s", "t", "mT", "MT", "Tr", "Fi", "mSi", "MSi", "mSv"]

flat_nums = ["1", "b2", "2", "b3", "3", "4", "b5", "5", "b6", "6", "b7", "7"]
sharp_nums = ["1", "#1", "2", "#2", "3", "4", "#4", "5", "#5", "6", "#6", "7"]

names = ["C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"]
names10EDO = ["C", "C#", "D", "D#", "E", "F#", "G", "G#", "A", "A#"]
names12EDO = ["C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"]
names18EDO = ["C", "C3t", "D3b", "D", "D3t", "E3b", "E", "E3t", "F3t", "F#", "G3b", "G3t", "G#", "A3b", "A3t", "A#", "B3b", "B3t"]
names22EDO = [str(i) for i in range(22)]
names24EDO = ["C", "Ct", "C#", "Dd", "D", "Dt", "D#", "Ed", "E", "Et", "F", "Ft", "F#", "Gd", "G", "Gt", "G#", "Ad", "A", "At", "A#", "Bd", "B", "Bt"]

# From https://github.com/masthom/Pitch-Class-Set-Calculator/blob/main/PCS_Calculator.py
base_forte_names = {"3-1": [0,1,2], "3-2": [0,1,3], "3-3": [0,1,4], "3-4": [0,1,5], "3-5": [0,1,6], "3-6": [0,2,4], "3-7": [0,2,5], "3-8": [0,2,6], "3-9": [0,2,7], "3-10": [0,3,6], "3-11": [0,3,7], "3-12": [0,4,8], "4-1": [0,1,2,3], "4-2": [0,1,2,4], "4-3": [0,1,3,4], "4-4": [0,1,2,5], "4-5": [0,1,2,6], "4-6": [0,1,2,7], "4-7": [0,1,4,5], "4-8": [0,1,5,6], "4-9": [0,1,6,7], "4-10": [0,2,3,5], "4-11": [0,1,3,5], "4-12": [0,2,3,6], "4-13": [0,1,3,6], "4-14": [0,2,3,7], "4-z15": [0,1,4,6], "4-16": [0,1,5,7], "4-17": [0,3,4,7], "4-18": [0,1,4,7], "4-19": [0,1,4,8], "4-20": [0,1,5,8], "4-21": [0,2,4,6], "4-22": [0,2,4,7], "4-23": [0,2,5,7], "4-24": [0,2,4,8], "4-25": [0,2,6,8], "4-26": [0,3,5,8], "4-27": [0,2,5,8], "4-28": [0,3,6,9], "4-z29": [0,1,3,7], "5-1": [0,1,2,3,4], "5-2": [0,1,2,3,5], "5-3": [0,1,2,4,5], "5-4": [0,1,2,3,6], "5-5": [0,1,2,3,7], "5-6": [0,1,2,5,6], "5-7": [0,1,2,6,7], "5-8": [0,2,3,4,6], "5-9": [0,1,2,4,6], "5-10": [0,1,3,4,6], "5-11": [0,2,3,4,7], "5-z12": [0,1,3,5,6], "5-13": [0,1,2,4,8], "5-14": [0,1,2,5,7], "5-15": [0,1,2,6,8], "5-16": [0,1,3,4,7], "5-z17": [0,1,3,4,8], "5-z18": [0,1,4,5,7], "5-19": [0,1,3,6,7], "5-20": [0,1,5,6,8], "5-21": [0,1,4,5,8], "5-22": [0,1,4,7,8], "5-23": [0,2,3,5,7], "5-24": [0,1,3,5,7], "5-25": [0,2,3,5,8], "5-26": [0,2,4,5,8], "5-27": [0,1,3,5,8], "5-28": [0,2,3,6,8], "5-29": [0,1,3,6,8], "5-30": [0,1,4,6,8], "5-31": [0,1,3,6,9], "5-32": [0,1,4,6,9], "5-33": [0,2,4,6,8], "5-34": [0,2,4,6,9], "5-35": [0,2,4,7,9], "5-z36": [0,1,2,4,7], "5-z37": [0,3,4,5,8], "5-z38": [0,1,2,5,8], "6-1": [0,1,2,3,4,5], "6-2": [0,1,2,3,4,6], "6-z3": [0,1,2,3,5,6], "6-z4": [0,1,2,4,5,6], "6-5": [0,1,2,3,6,7], "6-z6": [0,1,2,5,6,7], "6-7": [0,1,2,6,7,8], "6-8": [0,2,3,4,5,7], "6-9": [0,1,2,3,5,7], "6-z10": [0,1,3,4,5,7], "6-z11": [0,1,2,4,5,7], "6-z12": [0,1,2,4,6,7], "6-z13": [0,1,3,4,6,7], "6-14": [0,1,3,4,5,8], "6-15": [0,1,2,4,5,8], "6-16": [0,1,4,5,6,8], "6-z17": [0,1,2,4,7,8], "6-18": [0,1,2,5,7,8], "6-z19": [0,1,3,4,7,8], "6-20": [0,1,4,5,8,9], "6-21": [0,2,3,4,6,8], "6-22": [0,1,2,4,6,8], "6-z23": [0,2,3,5,6,8], "6-z24": [0,1,3,4,6,8], "6-z25": [0,1,3,5,6,8], "6-z26": [0,1,3,5,7,8], "6-27": [0,1,3,4,6,9], "6-z28": [0,1,3,5,6,9], "6-z29": [0,2,3,6,7,9], "6-30": [0,1,3,6,7,9], "6-31": [0,1,4,5,7,9], "6-32": [0,2,4,5,7,9], "6-33": [0,2,3,5,7,9], "6-34": [0,1,3,5,7,9], "6-35": [0,2,4,6,8,10], "6-z36": [0,1,2,3,4,7], "6-z37": [0,1,2,3,4,8], "6-z38": [0,1,2,3,7,8], "6-z39": [0,2,3,4,5,8], "6-z40": [0,1,2,3,5,8], "6-z41": [0,1,2,3,6,8], "6-z42": [0,1,2,3,6,9], "6-z43": [0,1,2,5,6,8], "6-z44": [0,1,2,5,6,9], "6-z45": [0,2,3,4,6,9], "6-z46": [0,1,2,4,6,9], "6-z47": [0,1,2,4,7,9], "6-z48": [0,1,2,5,7,9], "6-z49": [0,1,3,4,7,9], "6-z50": [0,1,4,6,7,9], "7-1": [0,1,2,3,4,5,6], "7-2": [0,1,2,3,4,5,7], "7-3": [0,1,2,3,4,5,8], "7-4": [0,1,2,3,4,6,7], "7-5": [0,1,2,3,5,6,7], "7-6": [0,1,2,3,4,7,8], "7-7": [0,1,2,3,6,7,8], "7-8": [0,2,3,4,5,6,8], "7-9": [0,1,2,3,4,6,8], "7-10": [0,1,2,3,4,6,9], "7-11": [0,1,3,4,5,6,8], "7-z12": [0,1,2,3,4,7,9], "7-13": [0,1,2,4,5,6,8], "7-14": [0,1,2,3,5,7,8], "7-15": [0,1,2,4,6,7,8], "7-16": [0,1,2,3,5,6,9], "7-z17": [0,1,2,4,5,6,9], "7-z18": [0,1,4,5,6,7,9], "7-19": [0,1,2,3,6,7,9], "7-20": [0,1,2,5,6,7,9], "7-21": [0,1,2,4,5,8,9], "7-22": [0,1,2,5,6,8,9], "7-23": [0,2,3,4,5,7,9], "7-24": [0,1,2,3,5,7,9], "7-25": [0,2,3,4,6,7,9], "7-26": [0,1,3,4,5,7,9], "7-27": [0,1,2,4,5,7,9], "7-28": [0,1,3,5,6,7,9], "7-29": [0,1,2,4,6,7,9], "7-30": [0,1,2,4,6,8,9], "7-31": [0,1,3,4,6,7,9], "7-32": [0,1,3,4,6,8,9], "7-33": [0,1,2,4,6,8,10], "7-34": [0,1,3,4,6,8,10], "7-35": [0,1,3,5,6,8,10], "7-z36": [0,1,2,3,5,6,8], "7-z37": [0,1,3,4,5,7,8], "7-z38": [0,1,2,4,5,7,8], "8-1": [0,1,2,3,4,5,6,7], "8-2": [0,1,2,3,4,5,6,8], "8-3": [0,1,2,3,4,5,6,9], "8-4": [0,1,2,3,4,5,7,8], "8-5": [0,1,2,3,4,6,7,8], "8-6": [0,1,2,3,5,6,7,8], "8-7": [0,1,2,3,4,5,8,9], "8-8": [0,1,2,3,4,7,8,9], "8-9": [0,1,2,3,6,7,8,9], "8-10": [0,2,3,4,5,6,7,9], "8-11": [0,1,2,3,4,5,7,9], "8-12": [0,1,3,4,5,6,7,9], "8-13": [0,1,2,3,4,6,7,9], "8-14": [0,1,2,4,5,6,7,9], "8-z15": [0,1,2,3,4,6,8,9], "8-16": [0,1,2,3,5,7,8,9], "8-17": [0,1,3,4,5,6,8,9], "8-18": [0,1,2,3,5,6,8,9], "8-19": [0,1,2,4,5,6,8,9], "8-20": [0,1,2,4,5,7,8,9], "8-21": [0,1,2,3,4,6,8,10], "8-22": [0,1,2,3,5,6,8,10], "8-23": [0,1,2,3,5,7,8,10], "8-24": [0,1,2,4,5,6,8,10], "8-25": [0,1,2,4,6,7,8,10], "8-26": [0,1,3,4,5,7,8,10], "8-27": [0,1,2,4,5,7,8,10], "8-28": [0,1,3,4,6,7,9,10], "8-z29": [0,1,2,3,5,6,7,9], "9-1": [0,1,2,3,4,5,6,7,8], "9-2": [0,1,2,3,4,5,6,7,9], "9-3": [0,1,2,3,4,5,6,8,9], "9-4": [0,1,2,3,4,5,7,8,9], "9-5": [0,1,2,3,4,6,7,8,9], "9-6": [0,1,2,3,4,5,6,8,10], "9-7": [0,1,2,3,4,5,7,8,10], "9-8": [0,1,2,3,4,6,7,8,10], "9-9": [0,1,2,3,5,6,7,8,10], "9-10": [0,1,2,3,4,6,7,9,10], "9-11": [0,1,2,3,5,6,7,9,10], "9-12": [0,1,2,4,5,6,8,9,10]}
enhanced_forte_names = {}

scales = {}

def gen_note_names(edo):
  return [str(i) for i in range(edo)]

def add(pc, interval):
  return (pc + interval) % EDO

def transpose(scale, interval):
  return [add(s, interval) for s in scale]

def mult(pc, interval):
  return (pc * interval) % EDO

def multiply(scale, interval):
  return [mult(s, interval) for s in scale]

def permute(scale, permutation):
  return [permutation[s] for s in scale]

# Variation on inversion with a "twist"
def twist_inversion(scale):
  return permute(scale, [
    0, 8, 7,
    9, 11, 10,
    6, 2, 1,
    3, 5, 4
    ])

def merge(sc1, sc2):
  res = sc1.copy()
  res.extend(sc2)
  return sortScale(res)

# Gets a PC set from a string like "t, t, s, t, t, t, s"
def set_PCs_from_imap_string(imap_txt):
  imap_txt = imap_txt.replace(" ", "")
  if "," not in imap_txt:
    raise ValueError("imap_txt must be comma delimited")
  imap_txt = imap_txt.split(",")
  pcs = [0]
  imap_names = ("s", "t", "mT", "MT", "Fo", "Tr", "Fi")
  for i in imap_txt:
    if i in imap_names:
      pcs.append(pcs[-1] + imap_names.index(i) + 1)
    else:
      raise ValueError("imap_txt must contain only standard interval codes, comma separators and optional whitespace")
  if pcs[-1] == 12:
    pcs = pcs[:-1]
    return pcs
  raise ValueError("imap_txt did not contain intervals that added to 12. NOTE: This function only works in 12EDO")

def sortScale(scale):
  return sorted(list(set(scale)))

def getAllScalesBySize(size, binary=False):
  ret = []
  for i in range(2**EDO):
    b = "{0:b}".format(i).zfill(EDO)
    if b.count("1") == size:
      if not binary:
        b = binaryToPCList(b)
      ret.append(b)
  return ret

def getUniqueScalesBySize(size):
  arr = getAllScalesBySize(size)
  ret = []
  for a in arr:
    include = True
    a = transpose(a, -1 * a[0])
    for r in ret:
      if equivalent(a, r):
        include = False
        break
    if include:
      ret.append(a)
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
  prev = EDO - scale[-1]
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
    if set(sortScale(transpose(small, i))).issubset(set(large)):
      return True
  return False

def findAllSubsetInstances(small, large):
  if len(small) >= len(large):
    return False
  retval = []
  for i in range(12):
    if set(sortScale(transpose(small, i))).issubset(set(large)):
      retval.append(i)
  return retval

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
          m = getModeNumber(pclist, cp)
          return {"Name": c[0], "Details": c[1], "Transpose": i, "Mode": m}
  return({"Name": "No matching scale for " + str(pclist) , "Details": None, "Transpose": 0} )

def getModeNameFromPCList(pcList):
  s = getScaleFromPCList(pcList)
  if s["Details"] is None:
    return ""
  return s["Details"]["ModeNames"][s["Mode"]]

# If scaleGroupRepresentative is mode 0, which mode is scale?
# Returns -1 if there's no match
def getModeNumber(scale, scaleGroupRepresentative):
  curr_mode = 0
  for i in range(0, 12):
    cp = sortScale(transpose(scaleGroupRepresentative, -i))
    if cp == scale:
      return curr_mode
    if 0 in cp:
      curr_mode += 1
  return -1

def binaryToPCList(bin):
  pcs = []
  for i, b in enumerate(bin):
    if b == "1":
      pcs.append(i)
  return pcs

def PCListToBinary(scale):
  result = ""
  for i in range(EDO):
    if i in scale:
      result += "1"
    else:
      result += "0"
  return result

def hammingDistance(scale1, scale2):
  scale1_bin = PCListToBinary(scale1)
  scale2_bin = PCListToBinary(scale2)
  min_diffs = 99
  for i in range(EDO):
    diffs = 0
    for j in range(EDO):
      if scale1_bin[(j + i) % EDO] != scale2_bin[j]:
        diffs += 1
    if diffs < min_diffs:
      min_diffs = diffs
  return min_diffs

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

def listContainsTransposition(scale, scaleList):
  for s in scaleList:
    if equalByTransposition(scale, s):
      return True
  return False

def findIdxOfModeInList(scale, scaleList):
  for i, s in enumerate(scaleList):
    if equivalent(scale, s):
      return i
  return -1

def make12EDOApproximation(scale, round=True):
  approx = []
  for i in scale:
    i *= 1200/EDO
    rem = i % 100
    pc = i / 100
    if round:
      pc = int(pc + 0.5)
    approx.append(pc)
  return approx

def getSpectrum(scale, n):
    if len(scale) == n:
        raise ValueError("len(scale) and n are equal")
    candidates = getUniqueScalesBySize(n)
    for c in candidates:
        if n < len(scale):
            spectrum = [c for c in candidates if isSubset(c, scale)]
        else:
            spectrum = [c for c in candidates if isSubset(scale, c)]
    return spectrum

def harmonize(superset, subset):
  # First we find an embedding of subset in superset.
  # If none is found, raise an error.
  for i in superset:
    candidate = sortScale(transpose(superset, -i))
    if set(subset).issubset(set(candidate)):
      break
  else:
    raise ValueError("It looks like your subset is not actually a subset of the superset")

  # Then produce the harmonization.
  return [
    [
      candidate[(candidate.index(n) + i) % len(candidate)]
      for n in subset
    ]
    for i in range(len(superset))
  ]

####################
# Forte Numbers etc
####################

def normalForm(scale):
    scale = sorted(list(set(scale)))
    candidates = getModes(scale)
    norm = candidates[0]
    for c in candidates[1:]:
      if getBestNormalFormCandidate(norm, c) == c:
        norm = c
    return norm

def getBestNormalFormCandidate(s1, s2):
    s1 = sorted(s1)
    s2 = sorted(s2)
    for i in range(len(s1)-1, -1, -1):
            d1 = add(s1[i], -1*s1[0])
            d2 = add(s2[i], -1*s2[0])
            if d1 < d2:
                    return s1
            if d2 < d1:
                    return s2
    return s1

def getForteName(scale):
  scale = normalForm(scale)
  for k in enhanced_forte_names:
    if enhanced_forte_names[k] == scale:
      return k
  return "ERROR: getForteName for " + str(scale)

def getNormalFromForteName(fn):
  return enhanced_forte_names[fn]

def getPrimeFromForteName(fn):
  return base_forte_names[fn]

def get_interval_vector(scale):
  iv = [0, 0, 0, 0, 0, 0]
  for i in range(0, len(scale)):
    for j in range(i + 1, len(scale)):
      interval = add(scale[j], -1 * scale[i])
      if interval > 6:
        interval = 12 - interval
      interval -= 1 # zero-based
      iv[interval] += 1
  return iv

def get_z_partner(scale):
  scale = normalForm(scale)
  iv = get_interval_vector(scale)
  fn = getForteName(scale)
  for b in base_forte_names:
    if b != fn and get_interval_vector(base_forte_names[b]) == iv:
      return base_forte_names[b]
  return None

def init_forte_names():
  global enhanced_forte_names
  for b in base_forte_names:
    sc = base_forte_names[b]
    sc_invert = normalForm(invert(sc))
    if sc == sc_invert:
      enhanced_forte_names[b] = sc
    else:
      enhanced_forte_names[b + "A"] = sc
      enhanced_forte_names[b + "B"] = sc_invert

"""
  The constellation of a PC set is the set of its inversion,
  M5 and M7 relations and the same for its z-partner (whichever
  of those exist). This is an equivalence class on PC sets.

  Caller should pass a normalized PC set and z = False.
"""
def constellation(norm_scale, z=False):
    new_con = [norm_scale]
    sM5 = normalForm(multiply(norm_scale, 5))
    if sM5 not in new_con:
        new_con.append(sM5)
    sM7 = normalForm(multiply(norm_scale, 7))
    if sM7 not in new_con:
        new_con.append(sM7)
    for cs in new_con:
        csi = normalForm(invert(cs))
        if csi not in new_con:
            new_con.append(csi)
    if not z:
        norm_scale_z = get_z_partner(norm_scale)
        if norm_scale_z is not None:
            new_con.extend(constellation(norm_scale_z, True))
    # Remove dupes
    new_con = [getNormalFromForteName(n) for n in list(set([getForteName(s) for s in new_con]))]
    return new_con

####################
# Scala
####################

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
  
def writeScalaFileFromCents(cents, name, desc="A tuning", folder=""):
  fname = name + ".scl"
  if folder != "":
    fname = folder + "/" + fname
  fname = "scala/" + fname
  f = open(fname, "w")
  f.write("!\n!\n")
  f.write(desc + "\n")
  f.write(" " + str(len(cents)) + "\n")
  f.write("!\n")
  for n in cents[1:]:
    f.write(" " + str(n) + "\n")
  f.write(" 2/1\n")
  f.close()


####################
# Serialism / Tone Rows
####################

def read_krn(path):
  base = []
  info = []
  with open(path) as f:
    for l in f:
      if len(l)>0:
        if l[0] == "!":
          info.append(l)
        elif l[0] != "*":
          base.append(int(l.split("\t")[0]))
  result = make_RI(base)
  result["Info"] = info
  return result

def transpose_row_to_zero(row):
  return [(p - row[0]) % 12 for p in row]

def make_RI(row):
  return {
    "O": transpose_row_to_zero(row),
    "R": transpose_row_to_zero(row[::-1]),
    "I": transpose_row_to_zero([12 - p for p in row]),
    "RI": transpose_row_to_zero([12 - p for p in row[::-1]])
    }

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

arpeggios12EDO = {
  (0, 4, 7): "major triad",
  (0, 3, 7): "minor triad",
  (0, 4, 8): "augmented triad",
  (0, 3, 6): "diminished triad",
  (0, 4, 7, 11): "major 7",
  (0, 4, 7, 10): "dom 7",
  (0, 4, 7, 9): "major 6",
  (0, 3, 7, 11): "minor-major 7",
  (0, 3, 7, 10): "minor 7",
  (0, 3, 7, 9): "minor 6",
  (0, 3, 6, 9): "dim 7",
  (0, 3, 6, 10): "m7b5"
}

def replaceLaTeX(s):
  # return s
  s = s.replace("flat", "b")
  s = s.replace("sharp", "#")
  s = s.replace("natural", "n")
  s = s.replace("$", "")
  s = s.replace("text", "")
  s = s.replace("^", "")
  s = s.replace("}", "")
  s = s.replace("{", "")
  return s

def init():
  global initialized
  global scales
  global noteNameToPC
  if initialized:
    return
  init_forte_names()
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
