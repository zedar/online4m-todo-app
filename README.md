# online4m.com - source code for TODO application.

[online4m](https://www.online4m.com) is service that belongs to family of PaaS BPMs - Platform as a Service BPMs. It is the most pragmatic way to developing workflow/process driven applications in the cloud.

## Installation procedure

### Step 1: download repository

  $ git clone https://github.com/zedar/online4m-todo-app.git

### Step 2: login into online4m

Login to your account in *online4m* service - [*online4m/Sign In*](http://online4m/login/auth).  
If you do not have account register new one - [*online4m/Sign Up*](http://online4m.com/usr/create).

### Step 3a: Import project archive

Folder *dist* contains file *ToDo.bar*. In *Navigator/Projects* tree right click on the root *Projects* and select *Import*.

Alternative approach is to import each artifact separatelly.

### Step 3b: Import artifacts

#### Step 3b.1: create new project

Name it for example TODO with Code Name TODO_APP.

### Step 3b.2: import form definition

Open form definition editor by selecting *New/Form Design* in toolbar under *Navigation* panel. Then select *Serialize Form Design to JSON*.  

In the dialog *GUI serialized to JSON* replace json string with the content of file:

    ./form/TODOFRM.json

Save form by selecting *Save Form Design* from the toolbar under *Navigation* panel.

In the dialog *Save Form Design* select project name (as was created in *Step 3*) and enter following attributes:

* *Code Name* - **TODOFRM**
* *Name* - **TODOFRM** 


### Step 3b.3: import process definition

Open process definition designer by selecting *New/Process Design* in toolbar under *Navigation* panel. Then select *Serializa Process Design to JSON*.  

In the dialog *Process serialized to JSON* replace json string with the content of file:

    ./process/TODOPROCESS.json

Save process by selecting *Save Process Design* from the toolbar under *Navigation* panel. 

In the dialog *Save Process Design* select project name (as was created in *Step 3*) and enter following attributes:

* *Code Name* - **TODOPROCESS**
* *Name* - **TODOPROCESS**

### Step 3b.4: import control rules

Open code editor by selecting *New/Control Rule File* in toolbar under *Navigation* panel.  
Copy and paste source code into the editor from the file:

    ./src/gui/gui_Main.groovy

Save ruleset by selecting *Save Control Ruleset* from the toolbar under *Navigation* panel. 

In the dialog *Save Control Ruleset* select project name (as was created in *Step 3*) and enter following attributes:

* *Code Name* - **gui_Main**

Repeat above steps with following files:

    ./src/gui/gui_Commons.groovy

### Step 4: run the process

From top menu select *Toolbox/Process Manager*. Find *Project: TODO[TODO_APP]* and then link to start process: *Process: TODO, TODO_APP*. Click it. Main form should be visible.

Running processes are visible on task list. From the top menu select *Toolbox/Form & Process Action*. Select *TODO* task list. Find your process instance on task list. Anytime you can double click on record from task list in order to continue further processing.
