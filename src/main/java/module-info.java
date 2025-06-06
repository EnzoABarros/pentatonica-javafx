module br.pentatonica.guitarrascrud {
    requires javafx.controls;
    requires javafx.fxml;


    opens br.pentatonica.guitarrascrud to javafx.fxml;
    exports br.pentatonica.guitarrascrud;
    exports br.pentatonica.guitarrascrud.guitarra.controllers;
    opens br.pentatonica.guitarrascrud.guitarra.controllers to javafx.fxml;
    opens br.pentatonica.guitarrascrud.guitarra to javafx.fxml;
    exports br.pentatonica.guitarrascrud.guitarra;
}