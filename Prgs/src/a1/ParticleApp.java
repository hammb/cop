package a1;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * ParticleApp is a simple Swing Application to visualize independently moving
 * particles. Particles must have their own life to move (a thread). Particle
 * must be implemented by yourself, this is the visualization, which is given
 * and must not even be understood; just run.
 */
public class ParticleApp extends JFrame {
    private static final long serialVersionUID = 3123790023447005992L;

    public final static int FPS = 20;
    public final static int MSPERFRAME = 1000 / FPS;

    private ParticleCanvas particleCanvas;
    private JButton addButton;
    private JButton add10Button;
    private JButton add100Button;
    private JButton add1000Button;
    private JButton clearButton;
    private JButton animateButton;
    private boolean paused;
    private JButton quitButton;
    private JLabel status;
    private List<Particle> particles;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ParticleApp particleApp = new ParticleApp();
                particleApp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                particleApp.pack();
                particleApp.setVisible(true);
            }
        });
    }

    private void add(int howMany) {
        while (howMany-- > 0) {
            Particle particle = new Particle();
            particles.add(particle);
        }
        updateStatus();
        particleCanvas.repaint();
    }
    
    private Runnable addRunnable = () -> { add(1); };
    private Runnable add10Runnable = () -> { add(10); };
    private Runnable add100Runnable = () -> { add(100); }; 
    private Runnable add1000Runnable = () -> { add(1000); }; 

    private void clear() {
        for (Particle p : particles) {
            p.stop();
        }
        particles.clear();
        updateStatus();
        particleCanvas.repaint();
    }

    private Runnable clearRunnable = () -> { clear(); };

    private void animate() {
        if (paused) {
            particleCanvas.cont();
            animateButton.setText("Pause");
        } else {
            particleCanvas.pause();
            animateButton.setText("Continue");
        }
        paused = !paused;
    }

    private Runnable animateRunnable = () -> { animate(); };
 
    private void quit() {
        clear();
        this.setVisible(false);
        particleCanvas.stop();
        this.dispose();
    }

    private Runnable quitRunnable = () -> { quit(); };

    private void setupUI() {
        setLocation(50, 50);
        FlowLayout mainLayout = new FlowLayout();
        mainLayout.setHgap(0);
        mainLayout.setVgap(0);
        JPanel panel = new JPanel(mainLayout);
        this.setContentPane(panel);
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        panel.add(buttonPanel);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0.1;
        gbc.weighty = 0.1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets = new Insets(6, 0, 0, 6);
        addButton = new ParticleButton("Add", addRunnable);
        buttonPanel.add(addButton, gbc);
        add10Button = new ParticleButton("Add10", add10Runnable);
        buttonPanel.add(add10Button, gbc);
        add100Button = new ParticleButton("Add100", add100Runnable);
        buttonPanel.add(add100Button, gbc);
        add1000Button = new ParticleButton("Add1000", add1000Runnable);
        buttonPanel.add(add1000Button, gbc);
        clearButton = new ParticleButton("Clear", clearRunnable);
        gbc.insets = new Insets(26, 0, 0, 6);
        buttonPanel.add(clearButton, gbc);
        animateButton = new ParticleButton("Animate", animateRunnable);
        buttonPanel.add(animateButton, gbc);
        quitButton = new ParticleButton("Quit", quitRunnable);
        buttonPanel.add(quitButton, gbc);
        gbc.insets = new Insets(99, 0, 0, 6); // cannot top align...
        buttonPanel.add(new JPanel(), gbc); // filler (hate GUIs)
        status = new JLabel();
        updateStatus();
        buttonPanel.add(status, gbc);
        particleCanvas = new ParticleCanvas();
        panel.add(particleCanvas);
    }

    private void updateStatus() {
        String s = String.format(" %05d particles", particles.size());
        status.setText(s);
    }

    public ParticleApp() {
        super("ParticleApp");
        particles = new ArrayList<>();
        setupUI();
        paused = true;
        animate();
    }

    private class ParticleButton extends JButton {
        private static final long serialVersionUID = 3829536079522361927L;

        public ParticleButton(String text, final Runnable callback) {
            super(text);
            this.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (callback != null) {
                        callback.run();
                    }
                }
            });
            this.setMargin(new Insets(6, 6, 6, 6));
        }
    }

    private class ParticleCanvas extends JPanel {
        private static final long serialVersionUID = 8405391768990281288L;

        private static final int RADIUS = 2;
        private int width, height; // as cache
        private Thread thread;

        public ParticleCanvas() {
            setBackground(Color.yellow);
            setPreferredSize(new Dimension(600, 600));
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            width = this.getSize().width;
            height = this.getSize().height;
            for (Particle p : particles) {
                drawParticle(g, p.getPosition());
            }
            // http://stackoverflow.com/questions/19480076/java-animation-stutters-when-not-moving-mouse-cursor
            // oh well...
            Toolkit.getDefaultToolkit().sync();
        }

        private void drawParticle(Graphics g, Point2D.Double point) {
            int x = (int) (point.x * width);
            int y = (int) (point.y * height);
            g.fillOval(x-RADIUS, y-RADIUS, RADIUS*2, RADIUS*2);
        }

        public void pause() {
            for (Particle p : particles) {
                p.pause();
            }
            thread = null;
        }

        public void cont() {
            for (Particle p : particles) {
                p.cont();
            }
            // display loop
            thread = new Thread() {
                public void run() {
                    while (Thread.currentThread() == thread) {
                        particleCanvas.repaint();
                        try {
                            Thread.sleep(MSPERFRAME);
                        } catch (InterruptedException e) {
                            System.err.println("interrupted");
                        }
                    }
                }
            };
            thread.start();
        }

        public void stop() {
            for (Particle p : particles) {
                p.stop();
            }
            thread = null;
        }
    }

}