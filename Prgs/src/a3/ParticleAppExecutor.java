package a3;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;

public class ParticleAppExecutor extends JFrame {
	private static final long serialVersionUID = 1778134548101548339L;
	
	public final static int FPS = 20;
	public final static int MSPERFRAME = 1000/FPS;

	private ParticleCanvas particleCanvas;
	private JButton addButton;
	private JButton add10Button;
	private JButton add100Button;
	private JButton clearButton;
	private JButton animateButton;	
	private boolean paused;
	private JButton quitButton;
	private JLabel status;

	// use either implementation
	// private Particles particles = new Particles();
	private ParticlesExecutor particles = new ParticlesExecutor();

	public ParticleAppExecutor() {
		super("ParticleAppExecutor");
		setupUI();
		paused = true;
		animate();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					ParticleAppExecutor particleApp = new ParticleAppExecutor();
					particleApp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					particleApp.pack();
					particleApp.setVisible(true);
				}
			});
	}

	private void add(int howMany) {
		while (howMany-- > 0) {
			particles.addParticle();
		}
		updateStatus();
	}
	private Runnable addRunnable = new Runnable() {
			@Override
			public void run() {
				add(1);
			}
		};
	private Runnable add10Runnable = new Runnable() {
			@Override
			public void run() {
				add(10);
			}
		};
	private Runnable add100Runnable = new Runnable() {
			@Override
			public void run() {
				add(100);
			}
		};
	private Runnable clearRunnable = new Runnable() {
			@Override
			public void run() {
				particles.clear();
				updateStatus();
			}
		};

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
	private Runnable animateRunnable = new Runnable() {
			@Override
			public void run() {
				animate();
			}
		};

	private Runnable quitRunnable = new Runnable() {
			@Override
			public void run() {
				particleCanvas.stop();
				ParticleAppExecutor.this.setVisible(false);
				ParticleAppExecutor.this.dispose();
			}
		};

	private void setupUI() {
		setLocation(50,50);
		FlowLayout mainLayout = new FlowLayout();
		mainLayout.setHgap(0); mainLayout.setVgap(0);
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
		String s = String.format(" %05d particles", 
								 particles.size());
		status.setText(s);
	}

	private class ParticleButton extends JButton {
		private static final long serialVersionUID = 7219118211449179142L;

		public ParticleButton(String text, 
						   final Runnable callback) {
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
		private static final long serialVersionUID = 3447522389775666877L;
		private static final int RADIUS=2;
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
			for (a1.AbstractParticle p : particles) {
				drawParticle(g, p.getPosition());
			}
			
		}
		private void drawParticle(Graphics g, Point2D.Double point) {
			int x = (int) (point.x*width);
			int y = (int) (point.y*height);
			g.fillOval(x-RADIUS, y-RADIUS, RADIUS*2, RADIUS*2);
		}
		public void pause() { 
			particles.pause();
			thread = null;
		}

		public void cont() {
			particles.cont();
			thread = new Thread() {
					public void run() {
						while (Thread.currentThread() == thread) {
							long start = System.currentTimeMillis();
							particleCanvas.repaint();
							try {
								long used = System.currentTimeMillis()-start;
								if (used < MSPERFRAME) {
									Thread.sleep(MSPERFRAME-used);
								} 
							} catch (InterruptedException ignored) {
							}
						}
					} 
				};
			thread.start();
		}
		public void stop() {
			particles.stop();
			thread = null;
		}
	}
	
}
