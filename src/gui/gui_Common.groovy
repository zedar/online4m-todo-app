package gui

//-------------------------
// Common functionality for InputHandlers
//-------------------------

class ToDoInputHandler extends InputHandler {
  void onInit() {
    if (processStep != "EDIT") {
      self.setReadOnly(true)
    }
  }  
}