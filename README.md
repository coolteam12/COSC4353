# SSHide

- Sunny Tran, Ryan Torrecampo, Frank Jaymez, Alex Bien
- Text editor made in JavaFX

- Make sure that you have Java Version 12.0.2 in C:/Program Files/Java/sdk-12.0.2
- Make sure that you have javafx-sdk-12.0.2
- Go to File > Project Structure
    - > Project
        - Under Project SDK:, make sure it's set to Version 12 "java version 12.0.2"
        - Under Project Language Level: make sure it's set to "12 - no new language features"
    - > Modules
        - Make sure Language Level is set to "12 - no new language features"
    - > Libraries
        - Make sure you download "richtextfx-fat-0.10.2.jar"
            - Delete the current file path in red for this file
            - Click the "+" button to add a new file path to wherever the "richtextfx-fat-0.10.2.jar" is located
    - > Global Libraries
        - if you don't have javafx-swt in the directory, click "+" > java and then go to Program Files(x86) > javafx-sdk-12.0.2 > lib and select all the files excluding the src zip file
        - if you don't have the file "javafx-sdk-12.0.2" then download it
