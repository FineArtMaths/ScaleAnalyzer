from kivy.app import App
from kivy.uix.boxlayout import BoxLayout
from kivy.uix.button import Button
from kivy.uix.textinput import TextInput

import scales as sc

class MainApp(App):
  def build(self):
      sc.init()
      # Properties
      self.scale = []

      # Choose a layout
      main_layout = BoxLayout(orientation="vertical")

      # Create the widgets
      self.scale_display = TextInput(
          multiline=True, readonly=True, halign="right", font_size=25
      )
      main_layout.add_widget(self.scale_display)
      h_layout = BoxLayout()
      self.modes_display_left = TextInput(
          multiline=False, readonly=True, halign="right", font_size=15
      )
      h_layout.add_widget(self.modes_display_left)
      self.modes_display_right = TextInput(
          multiline=False, readonly=True, halign="left", font_size=15
      )
      h_layout.add_widget(self.modes_display_right)
      main_layout.add_widget(h_layout)
      buttons = [
          ["", "b2", "", "b3", "", "", "b5", "", "b6", "", "b7", ""],
          ["1", "",  "2", "",  "3", "4",  "", "5", "",  "6", "", "7"]
      ]
      for row in buttons:
          h_layout = BoxLayout()
          for label in row:
              button = Button(
                  text=label,
                  pos_hint={"center_x": 0.5, "center_y": 0.5},
              )
              button.bind(on_press=self.toggle_scale_note)
              h_layout.add_widget(button)
          main_layout.add_widget(h_layout)

      h_layout = BoxLayout()
      go_button = Button(
          text="Go", pos_hint={"center_x": 0.5, "center_y": 0.5}
      )
      go_button.bind(on_press=self.get_details)
      h_layout.add_widget(go_button)
      clear_button = Button(
          text="Reset", pos_hint={"center_x": 0.5, "center_y": 0.5}
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
    s = d["Name"] + "\n"
    s += str(d["Details"]["Notes"])
    self.scale_display.text = s
    s_left = ""
    s_right = ""
    for i, m in enumerate(d["Details"]["ModeNames"]):
      if i < len(d["Details"]["ModeNames"]) / 2:
        s_left += m + "\n"
      else:
        s_right += m + "\n"
    self.modes_display_left.text = s_left
    self.modes_display_right.text = s_right

  def clear_all(self, instance):
    self.scale = []
    self.update_scale()
