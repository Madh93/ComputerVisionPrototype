package gui.utils.histogram;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public abstract class AbstractHistogram {
    
    private String title = null;
    private String name = null;
    private ArrayList pixelArray = null;
    
    public AbstractHistogram(String title, String name) {
        
        this.title = title;
        this.name = name;
    }
    
    /**
     * Muestra el histograma con los datos ya calculados
     * y ordenados correctamente
     */        
    public void showHistogram(ArrayList pixelArray) {
        
        this.pixelArray = pixelArray;
        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                initAndShowFrame();
            }
        });        
        
    }
    
    /**
     * Crea la nueva ventana donde se mostrará el histograma
     * y invoca el hilo de ejecución de JavaFX
     */    
    private void initAndShowFrame() {
        
        JFrame frame = new JFrame(title + "(" + name + ")");
        JFXPanel fxPanel = new JFXPanel();
        frame.add(fxPanel);
        frame.setSize(600, 400);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Provisional

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                initFX(fxPanel);
            }
       });        
    }
    
    /**
     * Crea la escena y componentes de JavaFX
     */
    private void initFX(JFXPanel fxPanel) {
        Scene scene = createChart();
        fxPanel.setScene(scene);
    }
    
    /**
     * Devuelve la escena que contiene el histograma de color
     * @return  el gráfico del histograma
     */
    private Scene createChart() {

        // Elementos
        CategoryAxis x = new CategoryAxis();
        NumberAxis y = new NumberAxis();
        BarChart<String, Number> chart = new BarChart<>(x,y);
        
        // Nombres
        chart.setTitle(title);
        x.setLabel("Color");
        y.setLabel("Número de píxeles");
        
        // Introducir datos
        XYChart.Series serie = new XYChart.Series();

        for (int i=0; i<=255; i++)
            serie.getData().add(new XYChart.Data(String.valueOf(i), pixelArray.get(i)));
        
        // Añadir a la escena
        Scene scene = new Scene(chart,600,400);
        chart.getData().addAll(serie);

        return scene;
    }    
    
    /**
     * La cuenta de píxeles está ordenada por colores desordenados,
     * es necesario pasarlos a enteros (0..255) para mostrarlos ordenados
     * @return  Número de píxeles entre 0 y 255
     */    
    public HashMap<Integer, Integer> getColorToInteger(HashMap<Color, Integer> pixelsCount) {
        // TODO: provisional para imágenes en blanco y negro
        
        HashMap<Integer, Integer> pixelIntegerCount = new HashMap<Integer,Integer>();
        
        for (Entry<Color, Integer> entry : pixelsCount.entrySet()) {
            Color key = entry.getKey();
            Integer value = entry.getValue();
            
            pixelIntegerCount.put(key.getBlue(), value);  
        }
        
        return pixelIntegerCount;
    }

}