import java.io.File;

import controller.ControllerSnakeGame;

public class Main {
    public static void main(String[] args) {
        if(args.length!=2){
            System.out.println("usage : java -cp bin Main [map(string) player(1|0)]");
        }
        else{
            System.out.println(args[0]);
            File fichier=new File(args[0]);
            Boolean player;
            if (!fichier.isFile()){
                System.out.println("Not a file ! usage : java -cp bin Main [map(string) player(1|0)]");
                return;
            }
            else{
                switch (args[1]) {
                    case "1":
                        player=true;
                        break;
                    case "0":
                        player=false;
                        break;
                    default:
                        System.out.println("Player must be 0 or 1 usage : java -cp bin Main [map(string) player(1|0)]");
                        return;
                }
            }
            ControllerSnakeGame csg=new ControllerSnakeGame(args[0],player);
        }
        
    }
    
}
