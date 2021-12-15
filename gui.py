from kivy.app import App
from kivy.uix.boxlayout import BoxLayout
from kivy.uix.button import Button
from kivy.uix.textinput import TextInput
from kivy.core.window import Window
# import kivy.lang.builder import Builder

import scales as sc

class ScaleAnalyzerApp(App):
  def build(self):
      Window.size = (400, 400)
      sc.init()
      # Properties
      self.scale = []
      # Builder.load_file('ScaleAnalyzer.kv')

      # Choose a layout
      main_layout = BoxLayout(orientation="vertical")

      # Create the widgets
      self.scale_display = TextInput(
          multiline=False, readonly=True, halign="right", font_size=25,
          size_hint=(1, 0.6)

      )
      main_layout.add_widget(self.scale_display)
      h_layout = BoxLayout()
      self.modes_display_left = TextInput(
          multiline=False, readonly=True, halign="right", font_size=15,
          size_hint=(1, 1)
      )
      h_layout.add_widget(self.modes_display_left)
      self.modes_display_right = TextInput(
          multiline=False, readonly=True, halign="left", font_size=15,
          size_hint=(1, 1),
          background_color=(0, 0, 1, 1),
          color=(1, 1, 1, 1)
      )
      h_layout.add_widget(self.modes_display_right)
      main_layout.add_widget(h_layout)

      self.keyboard = [
          ["", "b2", "", "b3", "", "", "b5", "", "b6", "", "b7", ""],
          ["1", "",  "2", "",  "3", "4",  "", "5", "",  "6", "", "7"]
      ]
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
          main_layout.add_widget(h_layout)

      h_layout = BoxLayout(
        size_hint=(0.5, 0.3)
      )
      go_button = Button(
          text="Go", pos_hint={"center_x": 0.5, "center_y": 0.5},
          background_normal="",
          background_color=(0.9, 1, 0.9, 1),
          color=(1, 1, 1, 1)
      )
      go_button.bind(on_press=self.get_details)
      h_layout.add_widget(go_button)

      clear_button = Button(
          text="Reset", pos_hint={"center_x": 0.5, "center_y": 0.5},
          background_normal="",
          background_color=(1, 0.9, 0.9, 1),
          color=(1, 1, 1, 1)
      )
      clear_button.bind(on_press=self.clear_all)
      h_layout.add_widget(clear_button)
      main_layout.add_widget(h_layout)

      return main_layout

  def toggle_scale_note(self, instance):
    button_text = instance.text
    if button_text != "":
      if button_text in self.scale:
        self.scale.remove(button_text)
      else:
        self.scale.append(button_text)
    self.update_scale()

  def getPCList(self):
    scale_PCs = []
    for i in range(sc.EDO):
      if sc.flat_nums[i] in self.scale:
        scale_PCs.append(i)
    return scale_PCs

  def update_scale(self):
    self.scale_display.text = str(sc.toNoteNames(self.getPCList()))
  
  def get_details(self, instance):
    d = sc.getScaleFromPCList(self.getPCList())
    if d == None or d["Details"] == None:
      self.scale_display.text = "Scale not found"
      self.modes_display_left.text = ""
      self.modes_display_right.text = ""
      return
    s = d["Name"] + "\n"
    s += str(d["Details"]["Notes"])
    self.scale_display.text = s
    s_left = ""
    s_right = ""
    for i, m in enumerate(d["Details"]["ModeNames"]):
      if i < len(d["Details"]["ModeNames"]) / 2:
        s_left += sc.replaceLaTeX(m) + "\n"
      else:
        s_right += sc.replaceLaTeX(m) + "\n"
    self.modes_display_left.text = s_left
    self.modes_display_right.text = s_right

  def clear_all(self, instance):
    self.scale = []
    self.update_scale()
