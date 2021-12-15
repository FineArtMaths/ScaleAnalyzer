from kivy.app import App
from kivy.uix.boxlayout import BoxLayout
from kivy.uix.label import Label
from kivy.uix.button import Button
from kivy.uix.textinput import TextInput
from kivy.core.window import Window
# import kivy.lang.builder import Builder

import scales as sc

d = 0.1
l = 0.3
red = (l, d, d, 1)
green = (d, l, d, 1)
lgreen = (d*2, l*2, d*2, 1)
blue = (d, d, l, 1)
white = (1, 1, 1, 1)
black = (0, 0, 0, 1)

class ScaleAnalyzerApp(App):
  def build(self):
      Window.size = (400, 400)
      sc.init()
      # Properties
      self.scale = []
      # Builder.load_file('ScaleAnalyzer.kv')

      # Choose a layout
      main_layout = BoxLayout(orientation="vertical")

      self.scale_display = Button(
          font_size=25,
          size_hint=(1, 0.6),
          border=(0,0,0,0),
          background_normal="",
          background_color=blue,
          color=white
      )
      main_layout.add_widget(self.scale_display)
      h_layout = BoxLayout()
      self.modes_display_left = Button(
          font_size=15,
          size_hint=(1, 1),
          border=(0,0,0,0),
          background_normal="",
          background_color=blue,
          color=white
      )
      h_layout.add_widget(self.modes_display_left)
      self.modes_display_right = Button(
          font_size=15,
          size_hint=(1, 1),
          border=(0,0,0,0),
          background_normal="",
          background_color=blue,
          color=white
      )
      h_layout.add_widget(self.modes_display_right)
      main_layout.add_widget(h_layout)

      self.keyboard = [
          ["", "b2", "", "b3", "", "", "b5", "", "b6", "", "b7", ""],
          ["1", "",  "2", "",  "3", "4",  "", "5", "",  "6", "", "7"]
      ]
      self.keys = []
      for r, row in enumerate(self.keyboard):
          h_layout = BoxLayout(
            size_hint=(1, 0.5)
          )
          for label in row:
              if r == 0 and label != "":
                bgc = (0, 0, 0, 1)  
                fgc = (0.9, 0.9, 0.9, 1)
              else:
                bgc = (0.9, 0.9, 0.9, 1)  
                fgc = (0, 0, 0, 1)
              button = Button(
                  text=label,
                  pos_hint={"center_x": 0.5, "center_y": 0.5},
                  border=(0,0,0,0),
                  background_normal="",
                  background_color=bgc,
                  color=fgc
              )
              button.bind(on_press=self.toggle_scale_note)
              h_layout.add_widget(button)
              self.keys.append(button)
          main_layout.add_widget(h_layout)

      h_layout = BoxLayout(
        size_hint=(1, 0.3)
      )
      go_button = Button(
          text="Go",
          border=(0,0,0,0),
          background_normal="",
          background_color=green,
          color=white
      )
      go_button.bind(on_press=self.get_details)
      h_layout.add_widget(go_button)

      clear_button = Button(
          text="Reset",
          border=(0,0,0,0),
          background_normal="",
          background_color=red,
          color=white
      )
      clear_button.bind(on_press=self.clear_all)
      h_layout.add_widget(clear_button)

      search_text = TextInput(
        size_hint=(2, 1)
      )
      h_layout.add_widget(search_text)

      main_layout.add_widget(h_layout)

      return main_layout

  def toggle_scale_note(self, instance):
    button_text = instance.text
    if button_text != "":
      if button_text in self.scale:
        self.scale.remove(button_text)
        instance.background_color = black if button_text[0] == "b" else white
      else:
        self.scale.append(button_text)
        instance.background_color=green
    self.update_scale()

  def getPCList(self):
    scale_PCs = []
    for i in range(sc.EDO):
      if sc.flat_nums[i] in self.scale:
        scale_PCs.append(i)
    return scale_PCs

  def update_scale(self):
    pcs = str(self.getPCList())
    self.scale_display.text = pcs + "\n" + str(sc.toNoteNames(self.getPCList()))
    self.syncKeyboardToScale()
  
  def get_details(self, instance):
    d = sc.getScaleFromPCList(self.getPCList())
    if d == None or d["Details"] == None:
      self.scale_display.text = "Scale not found"
      self.modes_display_left.text = ""
      self.modes_display_right.text = ""
      return
    s = d["Name"] + "\n"
    s += str(d["Details"]["Notes"])
    self.scale_display.text = sc.replaceLaTeX(s)
    s_left = ""
    s_right = ""
    for i, m in enumerate(d["Details"]["ModeNames"]):
      if i < len(d["Details"]["ModeNames"]) / 2:
        s_left += sc.replaceLaTeX(m) + "\n"
      else:
        s_right += sc.replaceLaTeX(m) + "\n"
    self.modes_display_left.text = s_left
    self.modes_display_right.text = s_right

  def loadScaleFromPCList(self, pcl=""):
    if pcl == "":
      pcl = self.search_text.text

  def syncKeyboardToScale(self):
    for k in self.keys:
      if k.text in self.scale:
        if len(k.text) > 1:
          k.background_color = green
        else:
          k.background_color = lgreen
      elif len(k.text) > 1: 
        k.background_color = black
      else:
         k.background_color = white


  def clear_all(self, instance):
    self.scale = []
    self.update_scale()
