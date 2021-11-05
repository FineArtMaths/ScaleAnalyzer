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

# NB This function creates a bunch of files
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
      sc.writeScalaFile(a, "12_from_24__Aug__" + str(num), "A semi-regular tuning of 12 notes from 24EDO based on the augmented triad")

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
      sc.writeScalaFile(a, "12_from_24__Dim__" + str(num), "A semi-regular tuning of 12 notes from 24EDO based on the diminished seventh chord")

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
      sc.writeScalaFile(a, "12_from_24__Tritone__" + str(num), "A semi-regular tuning of 12 notes from 24EDO based on the tritone")

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
      sc.writeScalaFile(a, "12_from_24__WT__" + str(num), "A semi-regular tuning of 12 notes from 24EDO based on the whole tone scale")


  sc.EDO = 12
  sc.names = sc.names12EDO
