package gui

//-------------------------
// FORM
//-------------------------


//-------------------------
// TODOLIST NAME
//-------------------------

class TODOLISTNAMEHandler extends ToDoInputHandler {
  void onValidate() {
    if (!self.getValue()) {
      self.setRequiredValidation("ToDo list has to have a name!")
    }
  }
}

//-------------------------
// NEWTODO
//-------------------------

class ROWNEWTODOHandler extends InputHandler {
  void onInit() {
    if (processStep != "EDIT") {
      self.setVisible(false)
    }
  }  
}

class BTNADDTODOHandler extends OnClickHandler {
  void onAction() {
    if (!NEWTODO.getValue()) {
      NEWTODO.setRequiredValidation("New todo text is required to add it to the list!")
      println("========> EMPTY NEW ToDo")
      return
    }
    model.addListRecord("ModelTODOFRM.ROWTODOLIST")
    def size = model.listSize("ModelTODOFRM.ROWTODOLIST")
    println("========> LIST SIZE: ${size}")
    if (size > 0) {
      println("========> Model item path: " + "ModelTODOFRM.ROWTODOLIST[${size-1}].TODOITEM")
      model.setValue("ModelTODOFRM.ROWTODOLIST[${size-1}].TODOITEM", NEWTODO.getValue())
      ROWTODOLIST[size-1].COLTODOITEM.TODOITEM?.setValue(NEWTODO.getValue())
      def listType = TODOITEMACTIVE.getValue()
      // if only completed items are visible
      if (listType == "C") {
        ROWTODOLIST[size-1].setVisible(false)
      }
      // recalculate number of left items
      int numOfItemsLeft = 0
      for (int i=0; i<size; i++) {
        def ic = ROWTODOLIST[i].TODOITEMCOMPLETED.TODOITEMCHECK
        boolean completed = (ic?.getValue()) == "1" ? true : false
        numOfItemsLeft += (completed) ? 0 : 1
      }
      TODOITEMLEFT.setText(numOfItemsLeft + " item${numOfItemsLeft == 1 ? '' : 's'} left")
    }
    NEWTODO.setValue("")
  }
}

//-------------------------
// TODOLIST ITEMS
//-------------------------

class BTNTODOREMOVEHandler extends OnClickHandler {
  void onInit() {
    if (processStep != "EDIT") {
      self.setVisible(false)
    }
  }
  
  void onAction() {
    def idx = self.listIndex()
    if (idx >= 0) {
      model.removeListRecord("ModelTODOFRM.ROWTODOLIST", idx)
    }
  }
}

class TODOITEMCHECKHandler extends ToDoInputHandler {
  void onChange() {
    def idx = self.listIndex()
    model.setValue("ModelTODOFRM.ROWTODOLIST[${idx}].TODOITEMCHECK", self.getValue())
    
    def size = model.listSize("ModelTODOFRM.ROWTODOLIST")
    int numOfItemsLeft = 0
    for (int i=0; i<size; i++) {
      def ic = ROWTODOLIST[i].TODOITEMCOMPLETED.TODOITEMCHECK
      boolean completed = (ic?.getValue()) == "1" ? true : false
      numOfItemsLeft += (completed) ? 0 : 1
    }
    TODOITEMLEFT.setText(numOfItemsLeft + " item${numOfItemsLeft == 1 ? '' : 's'} left")
  }
  
  List<String> getCompsToRefresh() { return ["TODOITEMLEFT"] }
}

class TODOITEMHandler extends ToDoInputHandler {
  void onValidate() {
    if (!self.getValue()) {
      self.setRequiredValidation("ToDo Item value has to be given!")
    }
  }
}

//-------------------------
// TODOLIST SUMMARY
//-------------------------

class TODOITEMACTIVEHandler extends InputHandler {
  void onInit() {
    def size = model.listSize("ModelTODOFRM.ROWTODOLIST")
    int numOfItemsLeft = 0
    for (int i=0; i<size; i++) {
      def val = model.getValue("ModelTODOFRM.ROWTODOLIST[${i}].TODOITEMCHECK")
      if (val) {
        boolean completed = (val == "1") ? true : false  
        numOfItemsLeft += (completed) ? 0 : 1
      }
      else {
        numOfItemsLeft += 1
      }
    }
    TODOITEMLEFT.setText(numOfItemsLeft + " item${numOfItemsLeft == 1 ? '': 's'} left")
    
    self.setValue("L")
  }
  
  void onChange() {
    def val = self.getValue()
    def size = model.listSize("ModelTODOFRM.ROWTODOLIST")
    for (int i=0; i<size; i++) {
      def ic = ROWTODOLIST[i].TODOITEMCOMPLETED.TODOITEMCHECK
      def completed = (ic.getValue()) == "1" ? true : false
      def visible = false
      if (val == "A") {
        // Show only active records
        visible = !completed ? true : false
      }
      else if (val == "C") {
        // show only completed
        visible = completed ? true : false
      }
      else if (val == "L") {
        visible = true
      }
      ROWTODOLIST[i].setVisible(visible)  
    }
  }
}

class BTNCLEARCOMPLETEDHandler extends OnClickHandler {
  void onInit() {
    if (processStep != "EDIT") {
      self.setVisible(false)
    }
  }
  
  void onAction() {
    def size = model.listSize("ModelTODOFRM.ROWTODOLIST")
    def idxToRemove = []
    for (int i=0; i<size; i++) {
      def ic = ROWTODOLIST[i].TODOITEMCOMPLETED.TODOITEMCHECK
      def completed = (ic.getValue()) == "1" ? true : false
      if (completed) {
        idxToRemove.add(i)
      }
      else {
        
      }
    }
    model.removeListRecords("ModelTODOFRM.ROWTODOLIST", idxToRemove)
  }
}

//-------------------------
// TODOLIST ACTION BUTTONS
//-------------------------

class BTNSAVELISTHandler extends OnClickHandler {
  void onInit() {
    if (processStep != "EDIT") {
      self.setVisible(false)
    }
  }
  
  void onAction() {
    if (!processStep) {
      return
    }
    NEWTODO.setValue("")
    // Validation of subsection requires full path
    if (!gui.validateSection("TODOFRM.TODOGRID.ROWTODOLIST")) {
      return
    }
    gui.saveSection("TODOFRM")
    gui.showMessage("ToDo List <strong>saved<strong>.", "success")
  }
}

class BTNBACKTOEDITHandler extends OnClickHandler {
  void onInit() {
    if (processStep != "ARCHIVED") {
      self.setVisible(false)
    }
  }
  
  void onAction() {
    process.jumpToTask("EDIT")
  }
}

class BTNARCHIVELISTHandler extends OnClickHandler {
  void onInit() {
    if (processStep != "EDIT") {
      self.setVisible(false)
    }
  }
  
  void onAction() {
    if (!processStep) {
      return
    }
    NEWTODO.setValue("")
    // Validate whole form
    if (!gui.validateSection("TODOFRM")) {
      return
    }
    gui.saveSection("TODOFRM")
    if (processStep == "EDIT") {
      process.executeTask("toARCHIVED")
    }
  }
}

class BTNCLOSELISTHandler extends OnClickHandler {
  void onInit() {
    if (processStep != "ARCHIVED") {
      self.setVisible(false)
    }
  }
  
  void onAction() {
    process.executeTask("toEND")
  }
}