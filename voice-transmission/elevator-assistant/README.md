

# Elevator Assistant Demo

### H/w & S/w Requirements

_**Software Requirements**_

     Bluemix Account
     Eclipse ( Windows / Linux ) ( Not needed if you are using Raspberry Pi Device )

_**Hardware Requirements**_

     Raspberry Pi 2 & above
     Head-set with Mic

### Get the Watson services on Bluemix

To successfully execute the Elevator Assistant demo, you would need the following four essential services of Watson:

     - Watson IoT Platform
     - Speech To Text
     - Text To Speech
     - Conversation
     - Weather Company Data

The above mentioned Watson services needs to binded to the **Node-RED stater kit**, that hosts the Node.js and Node-RED as default application, to start with.

Note down the credentials for each of the service, as we shall using them, as we work on deploying the appliation

### Import the Conversation workspace

As you begin to work with Watson Conversation service, you would need to define **Intents**, **Entities** and finally prepare a **Dialog**, based on the need & requirement. For the given Elevator Assistant demo, the required set of Intents, Entities & Dialog has been pre-configured into the [hotel-data workspace](https://github.ibm.com/Watson-IoT/Forester-Wave-Demo/blob/master/stage12/cognitive/elevator-assistant/hotel-data.json) and is made [available here](https://github.ibm.com/Watson-IoT/Forester-Wave-Demo/blob/master/stage12/cognitive/elevator-assistant/hotel-data.json), in a JSON format. 

_Launch the Conversation service and Import the hotel-data.json file into the workspace_. With that, the relevant data to get started with the Elevator Assistant demo, will be in place.

### Prepare the Environment on Raspberry Pi

The Node-RED needs to be set up, locally on the Raspberry Pi device. Along with the built-in nodes, the following set of nodes are needed in addtion:

1. Watson Nodes    : Set of Watson services, that include the STT, TTS and Conversation service

     **npm install node-red-node-watson**

2. Play Audio Node : A Node-RED node to play the audio in the browser. For further details [refer here.](http://flows.nodered.org/node/node-red-contrib-play-audio)

     **npm install node-red-contrib-play-audio**

### Configure the Node-RED Flow

The Node-RED flow that handles the Elevator Assistant conversation has been [made available on the Github repository](https://github.ibm.com/Watson-IoT/Forester-Wave-Demo/blob/master/stage12/cognitive/elevator-assistant/Elevator_Assistant_Flow_09_26.txt). Copy the clipboard contents and Import them into the Node-RED flow, using

     Import --> Clipboard -- Paste

Once the flow has been imported, edit the following nodes:

**Exec Node** : The Exec node, executes the following command on the Raspberry Pi Device, which opens up the Audio Recording channel for 3 seconds. Edit the option _-d 3_ to increase or decrease the number of seconds.

     arecord -d 3 -r 16000 -D plughw:1 /home/pi/temp/command.wav

**File In Node** : This file node, holds the location of the file _command.wav_, which has the actual recorded input from the user. This file is then fed into the Watson Speech to Text service.

**Watson Speech To Text Node** : Double click on the STT node and feed in the authentication credentials. Also, choose to select the Language as _English_ and Quality as _BroadbandModel_

**Watson Conversation Node** : Double click on the Conversation service and feed in the _Workspace ID_ (Note: The workspace created in the above step, would have a unique workspace ID. We should be using that here )

**Watson Text To Speech Node** : Double click on the TTS node and feed in the authentication credentials. Choose the target language as _English_, preference of Voice and have the Format as _WAV_

**IBM IoT Out Node** : Double click on the node and verify that the Device details entered, while you configured the IBM IoT In node are visible here. Else, configure them.

**HTTP Request Node** : Configure the HTTP Request node with the Weather API URL and use the Weather Company Data service credentials to access live weather data

Leave the rest of the nodes, as is.

### How to Initiate the execution

On the Elevator Assistant Node-RED flow, click on the Inject node, everytime you want to engage the Elevator Assistant into the conversation.

The Node-RED flow does the following:

1. Listens to the User Input for 3 seconds ( You can increase / decrease the time period )
2. Acknowledges the User with a message
3. Conversation service responds to the Input with the floor number, that will be sent to Watson IoT Platform ( This shall be consumed by the Elevator Simulator )
4. Based on the floor number, Elevator Assistant acknowledges the message, during the elevator movement
5. Once the Elevator stops at the destined floor, the directional message is voiced out by the Elevator Assistant


### Set of Questions that the Elevator Assistant is currently configured to answer

**Check-In**

     Room Number 303
     
     Three Zero Three
     
     303
     
     Take me to room 303
     
     My room number is Three Zero Three
     
     Go to Room 303
     
     My room is 303

**Check-Out**

     check out

     closing my stay

     done with my stay

     ending my stay

     finishing my stay

     I am checking out

     I want to check out

     moving out of hotel

     vacating my room

**Gym**

     feeling for work out

     go to gym

     i want to exercise

     I want to go to gym

     I want to work out

     I want to work out today

     location for work out

     take me to gym

**Spa**

     spa session

     want to have a spa session

     go to spa

     I want to go to spa

     spa

     take me to spa

     where is the spa


**Sight_seeing**

     going around the city

     going for sight seeing

     I want to go around the city

     sight seeing

     stepping out of the hotel

**Weather**

     is it cold

     is it going to rain

     is it going to rain today

     Is it hot

     is it raining

     is it sunny

     is it too cold today

     is it too hot outside

     what is the temperature outside

     will it rain today

     How is the weather outside

     how is today's weather

