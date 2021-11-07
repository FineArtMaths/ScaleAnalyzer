import scales as sc
#############################################################
# Utilities for working with Carnatic melakatas
#
# (C) Rich Cochrane 2020. All rights reserved.
# http://cochranemusic.com
#############################################################

# A chakra is a chakra_base plus 0 and a madhyama
chakra_bases = [
  [1, 2], [1, 3], [1, 4], [2, 3], [2, 4], [3, 4]
]
madhyama = [5, 6]
# The danis are added to any chakra to create a melakata
# (The name "dani" is my invention based on the svaras da and ni)
danis = [
  [8, 9], [8, 10], [8, 11], [9, 10], [9, 11], [10, 11]
]

melakatas = []
modal_relations = []

initialized = False
def init():
  global initialized
  if not initialized:
    build_melakatas()
    build_modal_relations()
    build_modal_groups()
    initialized = True

def build_melakatas():
  global melakatas
  for m in madhyama:
    for cb in chakra_bases:
        for d in danis:
          s = [0]
          s.extend(cb)
          s.append(m)
          s.append(7)
          s.extend(d) 
          melakatas.append(s)

def build_modal_relations():
  global modal_relations
  for m1 in melakatas:
    mr = []
    for i, m2 in enumerate(melakatas):
      if sc.equivalent(m1, m2):
        mr.append(i)
    modal_relations.append(mr)

modal_groups = {}
def build_modal_groups():
  global modal_groups
  for i, m in enumerate(melakatas):
    l = len(modal_relations[i])
    if l not in modal_groups.keys():
      modal_groups[l] = []
    if modal_relations[i] not in modal_groups[l]:
      modal_groups[l].append(modal_relations[i])

##########################################################
# Data
##########################################################

mel_names = [
  "kanakangi", "ratnangi", "ganamurti", "vanaspati", "manavati", "tanarupi", "senavati", "hanumatodi", "dhenuka", "natakapriya", "kokilapriya", "rupavati", "gayakapriya", "vakulabharan", "mayamalavagowla", "chakravakam", "suryakantam", "hatakambari", "jhamkaradhvani", "nathabhairavi", "klravani", "karaharapriya", "gowrmanohari", "varunapriya", "mararanji", "charukesi", "sarasangi", "harikambhoji", "dhlrasamkar abharanam", "naganandini", "yagapriya", "ragavardini", "gamgeyabhusani", "vagadlshvari", "sulini", "chalanata", "salagam", "jalarnavam", "jhalavarali", "navanitam", "pavani", "raghupriya", "shadvidanargini", "subhapantuvarali", "gavambodhi", "suvarnamgi", "divyamani", "bhavapriya", "dhavalambari", "namanarayani", "kamavardhini", "ramapriya", "gamanashrama", "vishvambhari", "samalangi", "shanmukapriya", "simhendramadhyamam", "hemavati", "dharmavati", "nitimati", "kantamani", "rishabhapriya", "latamgi", "vachaspati", "mechakalyani", "chitrambari", "sucharitra", "jyotisvarupini", "dhatuvardhini", "kosalam", "nasikabhushani", "risakapriya"
]

western_names = {
  28: "Major",
  22: "Melodic minor",
  20: "Harmonic Minor",
  26: "Harmonic Major",
  10: "Neapolitan",
  14: "Double Harmonic"
}