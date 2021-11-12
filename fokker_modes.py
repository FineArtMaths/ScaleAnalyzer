import scales as sc
import requests
#####################################################################
# Scrapes mode names off the list on the Fokker-Huygens site.
# Only gets the modes of EDOs; the others aren't needed for now. 
# I don't think these are especially useful but you never know.
#
# (C) Rich Cochrane 2020. All rights reserved.
# http://cochranemusic.com
#####################################################################

modes = []

def init():
  global modes
  session = requests.session()
  html = session.get("https://www.huygens-fokker.org/docs/modename.html").text
  heads = html.split("<H4>")
  heads = heads[1:]
  for h in heads:
    if "tone modes:" in h or "tone equal modes:" in h:
      edo = h.split(" tone ")[0].strip()
      try:
        edo = int(edo)
      except:
        continue
      sc.EDO = edo
      sc.names = sc.gen_note_names(edo)
      scales = h.split("<TT><B>")
      scales = scales[1:]
      mode_list = [] 
      for s in scales:
        name = s.split("<TD>")[1].split("</TD>")[0]
        s = s.split("</TD>")[0]
        s = s.split(" ")
        s = [int(n) for n in s]
        s = sc.fromIMap(s)
        modes.append({"Notes":s, "Name": name, "EDO": edo})

def get_modes_by_edo(edo):
  ret = []
  for m in modes:
    if m["EDO"] == edo:
      ret.append(m)
  return ret

def get_mode_by_name(name):
  name = format_name_for_matching(name)
  ret = []
  for m in modes:
    if format_name_for_matching(m["Name"]) == name:
      ret.append(m)
  return ret

def format_name_for_matching(name):
  return name.lower().strip().replace(" ", "")