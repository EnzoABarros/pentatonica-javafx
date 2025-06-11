module br.pentatonica.guitarrascrud {
    requires javafx.controls;
    requires javafx.fxml;


    opens br.pentatonica.guitarrascrud to javafx.fxml;
    exports br.pentatonica.guitarrascrud;
    exports br.pentatonica.guitarrascrud.guitarra.controllers;
    opens br.pentatonica.guitarrascrud.guitarra.controllers to javafx.fxml;
    opens br.pentatonica.guitarrascrud.guitarra to javafx.fxml;
    exports br.pentatonica.guitarrascrud.guitarra;

    opens br.pentatonica.guitarrascrud.usuarios to javafx.base, javafx.fxml;

    exports br.pentatonica.guitarrascrud.pagamentos;
    opens br.pentatonica.guitarrascrud.pagamentos to javafx.fxml;
    exports br.pentatonica.guitarrascrud.pagamentos.controllers;
    opens br.pentatonica.guitarrascrud.pagamentos.controllers to javafx.fxml;

}