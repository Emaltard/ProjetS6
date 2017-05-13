import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class Interface extends JComponent {

    private static int largeur = 640;
    private static int hauteur = 480;
    private ArrayList<Sommet> SommetSelec;
    private Point ptSouris;
    private Rectangle rectangleSouris;
    private boolean selectionEnCours;
    private MenuPanel control = new MenuPanel();
    private Graphe graphe;

    public static void main(String[] args) throws Exception{
        JFrame f = new JFrame("Graphe");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Interface inter = new Interface();
        f.add(inter.control, BorderLayout.NORTH);
        f.add(new JScrollPane(inter), BorderLayout.CENTER);
        f.setPreferredSize(new Dimension(largeur,hauteur));
        f.pack();
        f.setVisible(true);

        inter.run();
    }

    public Interface(){
        this.setOpaque(true);

    }

    public void run(){
        graphe = new GrapheListe();
        graphe.addSommet(new Point(50,50));
        graphe.addSommet(new Point(250,50));
        graphe.addSommet(new Point(50,350));
        graphe.addSommet(new Point(250,350));

        graphe.addArc(graphe.getSommet(0),graphe.getSommet(1));
        graphe.addArc(graphe.getSommet(0),graphe.getSommet(3));

    }

    public void paintComponent(Graphics g){
        g.setColor(Color.WHITE);
        g.fillRect(0,0,getWidth(),getHeight());


        for(Sommet s : graphe.get_liste_de_sommet()){
            g.setColor(s.getCouleur());
            g.fillOval(s.getPoint().x-10, s.getPoint().y-10, 20,20);
        }
        for(Arc a : graphe.get_liste_arc()){
            g.setColor(a.getCouleur());

        int x1 = a.getSommetDepart().getPoint().x;
        int y1 = a.getSommetDepart().getPoint().y;
        int x2 = a.getSommetArrivee().getPoint().x;
        int y2 = a.getSommetArrivee().getPoint().y;

            drawArrow(g, x1, y1, x2, y2);;
        }

    }

    void drawArrow(Graphics g1, int x1, int y1, int x2, int y2) {
        int ARR_SIZE = 12;
        Graphics2D g = (Graphics2D) g1.create();

        double dx = x2 - x1, dy = y2 - y1;
        double angle = Math.atan2(dy, dx);
        int len = (int) Math.sqrt(dx*dx + dy*dy);
        AffineTransform at = AffineTransform.getTranslateInstance(x1, y1);
        at.concatenate(AffineTransform.getRotateInstance(angle));
        g.transform(at);

        // Draw horizontal arrow starting in (0, 0)
        g.drawLine(0, 0, len, 0);
        g.fillPolygon(new int[] {len-10, len-ARR_SIZE-10, len-ARR_SIZE-10, len-10}, new int[] {0, -ARR_SIZE/2, ARR_SIZE/2, 0}, 4);
    }
}