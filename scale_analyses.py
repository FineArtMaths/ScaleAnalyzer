import scales as sc
import carnatic as c
import itertools


def melakata_modal_groups():
  print("Melakatas arranged into groups according to graha bhedam:")
  print()
  runtot = 0
  rungpcount = 0
  for k in sorted(c.modal_groups.keys()):
    if k > 1:
      names = []
      for g in c.modal_groups[k]:
        gn = []
        for s in g:
          mn = c.mel_names[s]
          if s in c.western_names.keys():
            mn = mn + " (" + c.western_names[s] + ")"
          gn.append(mn)
        names.append(gn)
      tot = len(c.modal_groups[k]) * k
      runtot += tot
      rungpcount += len(c.modal_groups[k])
      print(str(k) + " (count: " + str(len(c.modal_groups[k])) + ", " + str(tot) + " scales in total)")
      for n in names:
        print("     ", n)
      print()
  print()
  print(runtot, "melakatas out of 72 are in a modal group")
  print("So there are", 72 - runtot + rungpcount, "modal groups in total")

def nonMelakataHeptatonics():
  heptas = sc.getAllScalesBySize(7)
  ct = 0
  done = []
  for s in heptas:
    newGp = True
    for d in done:
      if sc.equivalent(d, s):
        newGp = False
    if newGp:
      done.append(s)
      isMela = False
      for m in c.melakatas:
        if sc.equivalent(s, m):
          isMela = True
          break
      if not isMela:
        modes = sc.getModes(s)
        scale = sc.getScaleFromPCList(s)
        if scale["Details"] is  None:
          for m in modes:
            if 0 in m and 7 in m:
              if 5 in m or 6 in m:
                print("    ", m, sc.toINames(m))
        ct += 1
  print(ct)

#########################################################
# Functions that write lots of Scala files
#########################################################

def semiRegular12NoteScalesIn24EDO():
  sc.EDO = 24
  sc.names = sc.names24EDO
  base = [0, 8, 16]

  print("Base:", sc.toNoteNames(base))
  print("==============================")
  options = list(range(1, base[1]))
  done = []
  num = 0
  combs = itertools.combinations(options, 3)
  for i in combs:
    a = []
    for b in base:
      a.append(b)
      for n in i:
        a.append(n + b)
    if not sc.isModeInList(a, done):
      num += 1
      print(sc.toNoteNames(a))
      done.append(a)
      sc.writeScalaFile(a, "12_from_24__Aug__" + str(num), "A semi-regular tuning of 12 notes from 24EDO based on the augmented triad", "12_from_24EDO")

  base = [0, 6, 12, 18]
  print()
  print("Base:", sc.toNoteNames(base))
  print("==============================")
  options = list(range(1, base[1]))
  done = []
  num = 0
  for i in itertools.combinations(options, 2):
    a = []
    for b in base:
      a.append(b)
      for n in i:
        a.append(n + b)
    if not sc.isModeInList(a, done):
      num += 1
      print(num, "  :  ", sc.toNoteNames(a))
      done.append(a)
      sc.writeScalaFile(a, "12_from_24__Dim__" + str(num), "A semi-regular tuning of 12 notes from 24EDO based on the diminished seventh chord", "12_from_24EDO")

  base = [0, 12]
  print()
  print("Base:", sc.toNoteNames(base))
  print("==============================")
  options = list(range(1, base[1]))
  done = []
  num = 0
  for i in itertools.combinations(options, int(12/len(base)) - 1):
    a = []
    for b in base:
      a.append(b)
      for n in i:
        a.append(n + b)
    if not sc.isModeInList(a, done):
      num += 1
      print(num, "  :  ", sc.toNoteNames(a))
      done.append(a)
      sc.writeScalaFile(a, "12_from_24__Tritone__" + str(num), "A semi-regular tuning of 12 notes from 24EDO based on the tritone", "12_from_24EDO")

  base = [0, 4, 8, 12, 16, 20]
  print()
  print("Base:", sc.toNoteNames(base))
  print("==============================")
  options = list(range(1, base[1]))
  done = []
  num = 0
  for i in itertools.combinations(options, int(12/len(base)) - 1):
    a = []
    for b in base:
      a.append(b)
      for n in i:
        a.append(n + b)
    if not sc.isModeInList(a, done):
      num += 1
      print(num, "  :  ", sc.toNoteNames(a))
      done.append(a)
      sc.writeScalaFile(a, "12_from_24__WT__" + str(num), "A semi-regular tuning of 12 notes from 24EDO based on the whole tone scale", "12_from_24EDO")
  sc.EDO = 12
  sc.names = sc.names12EDO

def twelveFrom18EDO():
  sc.EDO = 18
  sc.names = sc.names18EDO
  options = list(range(1, 18))
  bases = {"Tritone": [0, 9], "Aug": [0, 6, 12], "WT":[0, 3, 6, 9, 12, 15] }
  base_full_names = {"Tritone": "tritone", "Aug": "augmented triad", "WT":"whole tone scale" }
  for b in bases.keys():
    print(b)
    done = []
    num = 0
    available_notes = list(range(1, bases[b][1]))
    num_notes = 12 // len(bases[b]) - 1
    for i in itertools.combinations(available_notes, num_notes):
      a = []
      for n in bases[b]:
        a.append(n)
        for d in i:
          a.append(n + d)
      if not sc.isModeInList(a, done):
        num += 1
        print(num, "  :  ", sc.toNoteNames(a))
        done.append(a)      
        sc.writeScalaFile(a, "12_from_18__" + b + "__" + str(num), "Semi-regular 12-note tuning derived from 18EDO based on the " + base_full_names[b], "12_from_18EDO")
    print()

  sc.EDO = 12
  sc.names = sc.names12EDO

def melakata_tunings():
  print("12-note tunings where the white notes are a melakata and the black notes are exactly in between them. This produces mixtures of semitones, quarter-tones and three-quarter-tones")
  c.init()
  for j, m in enumerate(c.melakatas):
    s = []
    prev = -1
    for i, n in enumerate(m):
      if i not in [0, 3]:
        s.append(prev + (n - prev)/2)
      s.append(n)
      prev = n
    name = c.mel_names[j]
    sc.writeScalaFile(s, "mel__" + name, "12-note with the notes from " + name + " melakata on the white keys and notes exactly between them on the black keys", "melakata_tunings")
