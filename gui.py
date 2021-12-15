from kivy.app import App
from kivy.uix.boxlayout import BoxLayout
from kivy.uix.label import Label
from kivy.uix.button import Button
from kivy.uix.textinput import TextInput
from kivy.core.window import Window

# My "business logic"
import scales as sc

# Some colour constants that make it easy to tweak the coloru scheme later
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
      sc.init()
      self.scale = []

      Window.size = (400, 400)
      # The main layout will stack all our widgets vertically
      main_layout = BoxLayout(orientation="vertical")

      # Buttons make it easy to set background details; for some reason
      # they made this much mroe complicated for plain labels.
      self.scale_display = Button(
          font_size=25,
          size_hint=(1, 0.6),   # Proportion of the default width & height to use
          border=(0,0,0,0),     # Remove the bevelled edges, it's not 2003
          background_normal="", # Can put an image in here, but this si needed for a flat colour
          background_color=blue,
          color=white
      )
      main_layout.add_widget(self.scale_display)

      # Now for a box within the box
      h_layout = BoxLayout()    # Horizontal by default
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

      # Use a loop to generate a bunch of buttons in a piano keyboard shape.
      # Probably not great that the two rows of buttons are completely separate
      # BoxLayouts, and not ideal that the whitespace is made out of buttons.
      # But it works...
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

      # The two buttons and the TextEdit at the bottom.
      # NB the TextEdit doesn't do anything at the moment.
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

  # When the user clicks a piano key, turn the note on if it's off or off if it's on.
  # We're using the button text to do all the work here; it would be nicer
  # to subclass Button and make a button that can carry some custom information.
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

  # This shouldn't really be in the GUI code; I'm leaving it here temporarily.
  def getPCList(self):
    scale_PCs = []
    for i in range(sc.EDO):
      if sc.flat_nums[i] in self.scale:
        scale_PCs.append(i)
    return scale_PCs

  # If the scale changes, update the display and the piano keyboard
  def update_scale(self):
    pcs = str(self.getPCList())[1:-1]
    self.scale_display.text = pcs + "\n" + str(sc.toNoteNames(self.getPCList()))[1:-1]
    self.syncKeyboardToScale()

  # When the user hits "Go", go to the business logic code and get some info, 
  # then format it for display.  
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

  # If the scale changes we need to update the green highlights
  # on the piano keyboard.
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

  # Reset the app by clearing out the scale.
  # Don't forget to update the display afterwards!
  def clear_all(self, instance):
    self.scale = []
    self.modes_display_left.text = ""
    self.modes_display_right.text = ""
    self.scale_display.text = ""
    self.update_scale()
