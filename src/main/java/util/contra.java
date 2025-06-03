package util;

import org.mindrot.jbcrypt.BCrypt;

public class contra {

    // Método para generar hash bcrypt
    public static String generarHash(String passwordPlano) {
        // El número 10 representa el "cost" (complejidad)
        return BCrypt.hashpw(passwordPlano, BCrypt.gensalt(10));
    }

    // Método principal de prueba
    public static void main(String[] args) {
        String claveOriginal = "1234";
        String hashGenerado = generarHash(claveOriginal);

        System.out.println("Clave original: " + claveOriginal);
        System.out.println("Hash bcrypt: " + hashGenerado);
    }
}
