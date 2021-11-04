import scales as sc
import carnatic as c


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