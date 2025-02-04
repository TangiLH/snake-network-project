package view;

import javax.swing.*;

import controller.ControllerSnakeGame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ViewMap extends JFrame {

    public ViewMap() {
        setTitle("Select Map");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrer la fenêtre

        // Création du panel principal
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout()); 
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Récupération des noms de maps
        String[] mapNames = getMapList();
        JComboBox<String> comboBox = new JComboBox<>(mapNames);

        // Bouton "Play"
        JButton playButton = new JButton("Play");

     // Action lorsqu'on appuie sur "Play"
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedMap = (String) comboBox.getSelectedItem();
                System.out.println("Map sélectionnée : " + selectedMap) ; 
                ControllerSnakeGame csg=new ControllerSnakeGame("layouts/" + selectedMap,1);
                dispose();

            }
        });
        
        // Ajout des composants avec positionnement
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Select Map:"), gbc);

        gbc.gridx = 1;
        panel.add(comboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panel.add(playButton, gbc);

        add(panel);
        setVisible(true);
    }

    public static String[] getMapList() {
    	//mettre le bon fichier pour les maps 
        File folder = new File("layouts");
        List<String> mapList = new ArrayList<>();

        if (folder.exists() && folder.isDirectory()) {
        	//ignore ceux qui sont pas en .lay
            File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".lay")); 
            
            if (files != null && files.length > 0) {
                for (File file : files) {
                    mapList.add(file.getName()); 
                }
            }
        }

        // Retourne la liste sous forme de tableau (ou ["Aucune carte trouvée"] si vide)
        return mapList.isEmpty() ? new String[]{"Aucune carte trouvée"} : mapList.toArray(new String[0]);
    }
}