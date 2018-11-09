import receiver.Receiver;
import sender.Sender;
import utils.StringUtils;

import java.util.Arrays;

/**
 * La commande d’exécution de l’émetteur est :
 * % Sender <Nom_Machine> <Numero_Port> <Nom_fichier> <0>
 * La dernière option fait référence à l’utilisation de Go-Back-N (0).
 * La commande d’exécution du récepteur est :
 * % Receiver <Numero_Port>
 */




public class Main {

    public static void main(String args[]) {

        //Vérifie si les arguments du programme correspondent à ceux nécessaire pour partir un receveur
        if(args.length == 2 && args[0].toUpperCase().equals("RECEIVER") && StringUtils.isNumeric(args[1])) {

            int port = Integer.valueOf(args[1]);

            Receiver receiver = new Receiver(port);
            receiver.run();


            //Vérifie si les arguments du programme correspondent à ceux nécessaire pour partir un émetteur
        } else if (args.length == 4 && args[0].toUpperCase().equals("SENDER") && StringUtils.isNumeric(args[1])
                && StringUtils.isNumeric(args[3])) {

            int port = Integer.valueOf(args[1]);
            String filePath = args[2];
            int backN = Integer.valueOf(args[3]);

            Sender sender = new Sender(port, filePath, backN);
            sender.run();

        } else {
            System.out.print("Arguments du programme invalides : " + Arrays.toString(args));
        }
    }
}