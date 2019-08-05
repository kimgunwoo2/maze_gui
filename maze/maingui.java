package 실습;

import java.awt.*;

import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;

public class maingui extends JFrame implements ActionListener {
	JLabel status = new JLabel("메뉴화면");
	JLabel level = new JLabel("Level-중급");
	JLabel Round = new JLabel("");
	JPanel center_status;
	JPanel bottom_status;
	JPanel top_status;
	int hint_count = 0;
	int gamemode = 0;
	int sumtime = 0;
	int monster_room[][];
	int N = 25;
	MyKeyListener key_event = new MyKeyListener();

	ImageLabel room[][];
	File file_easy = new File("ranking_easy.txt");
	File file_middle = new File("ranking_middle.txt");
	File file_hard = new File("ranking_hard.txt");
	int count = 0;
	int row = 1, col = 0;
	maze maz;
	JLabel key_value = new JLabel("입력받는 Key : ");
	JLabel timer = new JLabel();
	TimerThread timer_th;
	Vector<String> ranking_save = new Vector<String>();
	ImageIcon road = new ImageIcon("길.jpg");
	ImageIcon hinticon = new ImageIcon("hint.png");
	ImageIcon monster = new ImageIcon("monster.jpg");
	ImageIcon brick = new ImageIcon("brick.jpg");
	ImageIcon cater = new ImageIcon("캐릭터.jpg");
	Timer logicTimer;

	void gamesetting() {

		center_status.removeAll();
		center_status.setLayout(new BorderLayout(0, 0));
		Component horizontalStrut = Box.createHorizontalStrut(140);
		center_status.add(horizontalStrut, BorderLayout.WEST);
		Component horizontalStrut_1 = Box.createHorizontalStrut(140);
		center_status.add(horizontalStrut_1, BorderLayout.EAST);
		Component verticalStrut = Box.createVerticalStrut(20);
		center_status.add(verticalStrut, BorderLayout.NORTH);
		bottom_status.add(status);

		JPanel meru_status = new JPanel();
		meru_status.setLayout(null);

		center_status.add(meru_status, BorderLayout.CENTER);
		JButton level_easy = new JButton("초급");
		level_easy.setFont(new Font("맑은 고딕", Font.PLAIN, 25));
		level_easy.setBackground(Color.CYAN);
		level_easy.setLocation(12, 79);
		level_easy.setSize(580, 103);
		meru_status.add(level_easy);
		level_easy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				level.setText("Level-초급");
				N = 19;

			}
		});

		JButton level_middle = new JButton("중급");
		level_middle.setFont(new Font("맑은 고딕", Font.PLAIN, 25));
		level_middle.setBackground(Color.magenta);
		level_middle.setLocation(12, 260);
		level_middle.setSize(580, 103);
		meru_status.add(level_middle);
		level_middle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				level.setText("Level-중급");
				N = 25;

			}
		});

		JButton level_hard = new JButton("상급");
		level_hard.setFont(new Font("맑은 고딕", Font.PLAIN, 25));
		level_hard.setLocation(12, 460);
		level_hard.setSize(580, 103);
		level_hard.setBackground(Color.pink);
		meru_status.add(level_hard);
		level_hard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				level.setText("Level-상급");
				N = 31;

			}
		});

		JButton exit = new JButton("이전으로");
		exit.setFont(new Font("맑은 고딕", Font.PLAIN, 25));
		exit.setLocation(12, 628);
		exit.setSize(580, 103);
		exit.setBackground(Color.YELLOW);
		meru_status.add(exit);

		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				status.setText("메뉴화면");
				mainpaint();

			}
		});
	}

	void ranking_read() {

		JPanel ranking_status = new JPanel();
		ranking_status.setLayout(new GridLayout(11, 1));

		JLabel Rankingready = new JLabel("※  랭    킹    순    위  ※");
		ranking_status.setOpaque(true);
		ranking_status.setBackground(Color.black);
		Rankingready.setFont(new Font("궁서", Font.PLAIN, 60));
		Rankingready.setForeground(Color.white);
		Rankingready.setHorizontalAlignment(SwingConstants.CENTER);
		ranking_status.add(Rankingready);
		status.setText("랭킹화면");
		try {
			Scanner scan;
			if (level.getText().equals("Level-초급"))
				scan = new Scanner(file_easy);
			else if (level.getText().equals("Level-중급"))
				scan = new Scanner(file_middle);
			else
				scan = new Scanner(file_hard);
			int i = 1;
			while (scan.hasNextLine()) {

				JLabel rankingtext = new JLabel(i + "등.  " + scan.nextLine());
				i++;
				rankingtext.setFont(new Font("궁서", Font.PLAIN, 30));
				rankingtext.setForeground(Color.white);
				rankingtext.setHorizontalAlignment(SwingConstants.CENTER);
				ranking_status.add(rankingtext);

			}

			scan.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JButton back = new JButton("이전으로");
		JButton reset = new JButton("랭킹 초기화");
		Component horizontalStrut = Box.createHorizontalStrut(30);
		reset.setBackground(Color.YELLOW);
		back.setBackground(Color.CYAN);
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				status.setText("메뉴화면");
				mainpaint();

			}
		});
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int result = 0;
				result = JOptionPane.showConfirmDialog(null, "정말 초기화 하시겠습니까?");
				if (result == 0) {
					try {
						BufferedWriter bufferedWriter;
						if (level.getText().equals("Level-초급")) {
							bufferedWriter = new BufferedWriter(new FileWriter(file_easy));
							bufferedWriter.flush();
						} else if (level.getText().equals("Level-중급")) {
							bufferedWriter = new BufferedWriter(new FileWriter(file_middle));
							bufferedWriter.flush();
						} else {
							bufferedWriter = new BufferedWriter(new FileWriter(file_hard));
							bufferedWriter.flush();
						}

					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					status.setText("랭킹 초기화 중");
					ranking_read();
				}

			}
		});
		top_status.removeAll();
		top_status.add(reset);
		top_status.add(horizontalStrut);
		top_status.add(back);
		center_status.removeAll();
		center_status.add(ranking_status);
		center_status.repaint();
	}

	void rankingwrite(String id) {
		String temp;
		int min = sumtime / 60;
		int sec = sumtime % 60;
		if (sec < 10)
			temp = min + "분 0" + sec + "초" + " " + id;
		else
			temp = min + "분 " + sec + "초" + " " + id;
		ranking_save.removeAllElements();
		ranking_save.add(temp);

		try {
			Scanner scan;
			if (level.getText().equals("Level-초급"))
				scan = new Scanner(file_easy);
			else if (level.getText().equals("Level-중급"))
				scan = new Scanner(file_middle);
			else
				scan = new Scanner(file_hard);
			while (scan.hasNextLine()) {

				ranking_save.add(scan.nextLine());

			}
			Collections.sort(ranking_save);
			if (ranking_save.size() == 11) {
				ranking_save.remove(10);
			}
			BufferedWriter bufferedWriter;
			if (level.getText().equals("Level-초급")) {

				bufferedWriter = new BufferedWriter(new FileWriter(file_easy));
				if (file_easy.isFile() && file_easy.canWrite()) {
					for (int i = 0; i < ranking_save.size(); i++) {
						bufferedWriter.write(ranking_save.get(i));
						bufferedWriter.newLine();

					}
					bufferedWriter.close();
				}
			} else if (level.getText().equals("Level-중급")) {
				bufferedWriter = new BufferedWriter(new FileWriter(file_middle));
				if (file_middle.isFile() && file_middle.canWrite()) {
					for (int i = 0; i < ranking_save.size(); i++) {
						bufferedWriter.write(ranking_save.get(i));
						bufferedWriter.newLine();

					}
					bufferedWriter.close();
				}
			} else {
				bufferedWriter = new BufferedWriter(new FileWriter(file_hard));
				if (file_hard.isFile() && file_hard.canWrite()) {
					for (int i = 0; i < ranking_save.size(); i++) {
						bufferedWriter.write(ranking_save.get(i));
						bufferedWriter.newLine();

					}
					bufferedWriter.close();
				}
			}

			scan.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	void boardcratemode() {
		row = 1;
		col = 0;
		hint_count = 0;
		room = new ImageLabel[N][N];
		center_status.removeAll();

		maz.start(N);
		Round.setText(" Round-" + (count + 1));
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				monster_room[i][j] = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				room[i][j] = new ImageLabel();
				room[i][j].setPreferredSize(new Dimension(50, 50));
				if (maz.board[i][j] == 1) {
					room[i][j].setImageIcon(brick);
				} else
					room[i][j].setImageIcon(road);
				center_status.add(room[i][j]);
			}
		}
		int j;
		if (level.getText().equals("Level-초급"))
			j = 1;
		else if (level.getText().equals("Level-중급"))
			j = 2;
		else
			j = 3;
		for (int i = 0; i < count*j-1; i++) {
			gamemode = 0;
			monster monster = new monster();
			monster.start();
		}

		room[row][col].setImageIcon(cater);

		key_value.setText("입력받는 Key : ");
		center_status.requestFocus();
		center_status.setFocusable(true);
	}

	void gamemodepaint() {

		JButton hint = new JButton("Hint");
		hint.setBackground(Color.PINK);
		Component horizontalStrut = Box.createHorizontalStrut(20);
		top_status.add(horizontalStrut);
		top_status.add(hint);
		JButton first = new JButton("Give up");
		first.setBackground(Color.GREEN);
		status.setText("게임화면");
		timer.setText("time: 00 sec");
		top_status.add(first);
		timer.setFont(new Font("맑은 고딕", Font.BOLD, 30));

		Component horizontalStrut1 = Box.createHorizontalStrut(20);
		top_status.add(horizontalStrut1);
		top_status.add(timer);
		top_status.repaint();
		// bottom_status.add(status);
		center_status.setLayout(new GridLayout(N, N));
		monster_room = new int[N][N];
		maz = new maze();
		boardcratemode();
		first.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (!timer.getText().equals("time: 00 sec"))
					timer_th.interrupt();
				status.setText("메뉴화면");
				mainpaint();
				repaint();
			}
		});
		Component horizontalStrut2 = Box.createHorizontalStrut(100);
		bottom_status.add(horizontalStrut2);
		bottom_status.add(key_value);
		center_status.removeKeyListener(key_event);
		center_status.addKeyListener(key_event);

		logicTimer = new Timer(1, this);
		logicTimer.start();

		hint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (hint_count < 3) {
					maz.st.removeAllElements();
					hint hint_th = new hint();
					hint_th.start();
					hint_count++;
				}
				center_status.requestFocus();
				center_status.setFocusable(true);
			}
		});
	}

	void mainpaint() {
		if (logicTimer != null)
			logicTimer.stop();
		logicTimer = null;

		center_status.removeAll();
		top_status.removeAll();
		bottom_status.removeAll();
		count = 0;
		sumtime = 0;
		center_status.setLayout(new BorderLayout(0, 0));
		Component horizontalStrut = Box.createHorizontalStrut(140);
		center_status.add(horizontalStrut, BorderLayout.WEST);
		Component horizontalStrut_1 = Box.createHorizontalStrut(140);
		center_status.add(horizontalStrut_1, BorderLayout.EAST);
		Component verticalStrut = Box.createVerticalStrut(20);
		center_status.add(verticalStrut, BorderLayout.NORTH);
		bottom_status.add(status);
		JLabel label = new JLabel("Game Ready");
		Round.setText("");
		JPanel meru_status = new JPanel();
		meru_status.setLayout(null);
		level.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		Round.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		label.setFont(new Font("맑은 고딕", Font.BOLD, 50));
		top_status.add(label);
		top_status.add(Box.createHorizontalStrut(30));
		top_status.add(level);
		top_status.add(Round);
		top_status.repaint();
		bottom_status.repaint();
		center_status.add(meru_status, BorderLayout.CENTER);
		JButton game_start = new JButton("게임시작");
		game_start.setFont(new Font("맑은 고딕", Font.PLAIN, 25));
		game_start.setBackground(Color.CYAN);
		game_start.setLocation(12, 79);
		game_start.setSize(580, 103);
		meru_status.add(game_start);

		JButton ranking = new JButton("랭킹");
		ranking.setFont(new Font("맑은 고딕", Font.PLAIN, 25));
		ranking.setBackground(Color.magenta);
		ranking.setLocation(12, 260);
		ranking.setSize(580, 103);
		meru_status.add(ranking);

		JButton game_setting = new JButton("환경설정");
		game_setting.setFont(new Font("맑은 고딕", Font.PLAIN, 25));
		game_setting.setLocation(12, 460);
		game_setting.setSize(580, 103);
		game_setting.setBackground(Color.pink);
		meru_status.add(game_setting);
		game_setting.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				status.setText("게임설정");
				gamesetting();
			}
		});

		JButton exit = new JButton("종료");
		exit.setFont(new Font("맑은 고딕", Font.PLAIN, 25));
		exit.setLocation(12, 628);
		exit.setSize(580, 103);
		exit.setBackground(Color.black);
		exit.setForeground(Color.white);
		meru_status.add(exit);

		game_start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				center_status.removeAll();
				gamemodepaint();
				label.setText("Game Start");
			}
		});
		ranking.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				ranking_read();

			}
		});
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
	}

	maingui(JPanel center_status, JPanel top_status, JPanel bottom_status) {
		JLabel status = new JLabel("처음화면");
		this.center_status = center_status;
		this.top_status = top_status;
		this.bottom_status = bottom_status;
	}

	class MyKeyListener extends KeyAdapter {
		@SuppressWarnings("deprecation")
		public void keyPressed(KeyEvent e) {
			int keyCode = e.getKeyCode();
			int befr = row;
			int befc = col;

			switch (keyCode) {
			case KeyEvent.VK_UP:
				key_value.setText("입력받는 Key : up      ");
				if (maz.board[row - 1][col] != 1 && maz.board[row - 1][col] != 3) {
					room[row][col].setImageIcon(road);
					room[row][col].repaint();
					row = row - 1;
					room[row][col].setImageIcon(cater);
					room[row][col].repaint();
				}
				break;
			case KeyEvent.VK_DOWN:
				key_value.setText("입력받는 Key : down");
				if (maz.board[row + 1][col] != 1 && maz.board[row + 1][col] != 3) {
					room[row][col].setImageIcon(road);
					room[row][col].repaint();
					row = row + 1;
					room[row][col].setImageIcon(cater);
					room[row][col].repaint();
				}
				if (row == N - 1 && col == N - 2) {
					gamemode = 1;
					sumtime = sumtime + timer_th.time - 1;
					timer_th.interrupt();
					count++;
					if (count == 3) {
						String id = JOptionPane.showInputDialog("랭킹 등록할 이름을 적으세요");
						while (id.equals("")) {
							id = JOptionPane.showInputDialog("랭킹 등록할 이름을 적으세요");
						}
						rankingwrite(id);
						status.setText("메뉴화면");
						mainpaint();

					} else {
						JOptionPane.showMessageDialog(null, "미로를 탈출하셨습니다. 다음 라운드로 넘어 갑니다.", "탈출성공",
								JOptionPane.INFORMATION_MESSAGE);

						boardcratemode();
						timer.setText("time: 00 sec");

					}
				}

				break;
			case KeyEvent.VK_LEFT:
				key_value.setText("입력받는 Key : left    ");
				if (col > 1 && maz.board[row][col - 1] != 1 && maz.board[row][col - 1] != 3) {
					room[row][col].setImageIcon(road);
					room[row][col].repaint();
					col = col - 1;
					room[row][col].setImageIcon(cater);
					room[row][col].repaint();

				}

				break;
			case KeyEvent.VK_RIGHT:
				key_value.setText("입력받는 Key : right  ");
				if (maz.board[row][col + 1] != 1 && maz.board[row][col + 1] != 3) {

					room[row][col].setImageIcon(road);
					room[row][col].repaint();
					col = col + 1;
					room[row][col].setImageIcon(cater);
					room[row][col].repaint();
				}
				if (row == 1 && col == 1 && befr == 1 && befc == 0) {
					gamemode = 0;
					timer_th = new TimerThread(timer, center_status, top_status);
					timer_th.start();
				}

				break;
			}
		}
	}

	class hint extends Thread {
		int i = 1;

		public hint() {

		}

		public void run() {
			center_status.requestFocus();
			center_status.setFocusable(true);
			int temp[][] = new int[N][N];
			for (int z = 0; z < N; z++) {
				for (int y = 0; y < N; y++) {
					temp[z][y] = maz.board[z][y];
				}
			}
			maz.find(temp, row, col, N);
			Stack<pos> st = new Stack<pos>();
			for (int z = 0; z < maz.st.size(); z++) {
				st.add(maz.st.get(z));
			}
			for (; i < maz.st.size(); i++) {
				if (i != 1) {
					room[st.get(i - 1).x][st.get(i - 1).y].setImageIcon(road);
					room[st.get(i - 1).x][st.get(i - 1).y].repaint();
				}
				room[st.get(i).x][st.get(i).y].setImageIcon(hinticon);
				room[st.get(i).x][st.get(i).y].repaint();
				try {
					sleep(30);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			room[st.get(i - 1).x][st.get(i - 1).y].setImageIcon(road);
			room[st.get(i - 1).x][st.get(i - 1).y].repaint();
			center_status.updateUI();
			this.interrupt();

		}
	}

	class TimerThread extends Thread {
		JLabel timerLabel;
		JPanel panel;
		JPanel top_status;
		int time;

		public TimerThread(JLabel timerLabel, JPanel panel, JPanel top_status) {
			this.timerLabel = timerLabel;
			this.panel = panel;
			this.top_status = top_status;

		}

		public void run() {
			time = 0;
			while (gamemode == 0) {
				if (time < 10)
					timerLabel.setText("time: 0" + Integer.toString(time) + " sec");
				else
					timerLabel.setText("time: " + Integer.toString(time) + " sec");
				time++;
				try {

					sleep(1000);

				} catch (InterruptedException e) {
					return;
				}
				if (time == 100) {
					gamemode = 1;
					this.interrupt();
					timerLabel.setText("time:" + Integer.toString(time) + " sec");
					JOptionPane.showMessageDialog(null, "  Time over ", "시간초과", JOptionPane.INFORMATION_MESSAGE);
					mainpaint();
					center_status.updateUI();
					timer.setText("time: 00 sec");

					return;
				}
			}

		}
	}

	class monster extends Thread {

		public void run() {
			int beforeramdom = 4;
			int monster_row, monster_low;
			Random ramdom = new Random();
			monster_row = ramdom.nextInt(N - 2) + 1;
			monster_low = ramdom.nextInt(N - 2) + 1;
			room[monster_row][monster_low].setImageIcon(monster);
			monster_room[monster_row][monster_low] = 3;
			while (gamemode == 0) {
				if (status.getText().equals("메뉴화면"))
					this.interrupt();

				try {
					int dect = ramdom.nextInt(4);
					while (((beforeramdom == 1) && (dect == 0)) || ((beforeramdom == 0) && (dect == 1)
							|| ((beforeramdom == 2) && (dect == 3) || ((beforeramdom == 3) && (dect == 2))))) {
						dect = ramdom.nextInt(4);
					}
					beforeramdom = dect;
					if (dect == 0 && monster_room[monster_row - 1][monster_low] != 3
							&& monster_room[monster_row + 1][monster_low] != 3) {
						if (maz.board[monster_row][monster_low] == 1) {
							room[monster_row][monster_low].setImageIcon(brick);

						} else {
							room[monster_row][monster_low].setImageIcon(road);

						}
						monster_room[monster_row][monster_low] = 0;
						room[monster_row][monster_low].repaint();

						if (monster_row != 1)
							monster_row = monster_row - 1;
						else
							monster_row = monster_row + 1;

						room[monster_row][monster_low].setImageIcon(monster);
						monster_room[monster_row][monster_low] = 3;
						room[monster_row][monster_low].repaint();

					} else if (dect == 1 && monster_room[monster_row + 1][monster_low] != 3
							&& monster_room[monster_row - 1][monster_low] != 3) {
						if (maz.board[monster_row][monster_low] == 1) {
							room[monster_row][monster_low].setImageIcon(brick);

						} else {
							room[monster_row][monster_low].setImageIcon(road);

						}
						monster_room[monster_row][monster_low] = 0;
						room[monster_row][monster_low].repaint();

						if (monster_row != N - 2)
							monster_row = monster_row + 1;
						else
							monster_row = monster_row - 1;

						room[monster_row][monster_low].setImageIcon(monster);
						monster_room[monster_row][monster_low] = 3;
						room[monster_row][monster_low].repaint();

					} else if (dect == 2 && monster_room[monster_row][monster_low + 1] != 3
							&& monster_room[monster_row][monster_low - 1] != 3) {
						if (maz.board[monster_row][monster_low] == 1) {
							room[monster_row][monster_low].setImageIcon(brick);

						} else {
							room[monster_row][monster_low].setImageIcon(road);

						}
						monster_room[monster_row][monster_low] = 0;
						room[monster_row][monster_low].repaint();

						if (monster_low != 1)
							monster_low = monster_low - 1;
						else
							monster_low = monster_low + 1;

						room[monster_row][monster_low].setImageIcon(monster);
						monster_room[monster_row][monster_low] = 3;
						room[monster_row][monster_low].repaint();

					} else if (dect == 3 && monster_room[monster_row][monster_low + 1] != 3
							&& monster_room[monster_row][monster_low - 1] != 3) {
						if (maz.board[monster_row][monster_low] == 1) {
							room[monster_row][monster_low].setImageIcon(brick);

						} else {
							room[monster_row][monster_low].setImageIcon(road);

						}
						monster_room[monster_row][monster_low] = 0;
						room[monster_row][monster_low].repaint();

						if (monster_low != N - 2)
							monster_low = monster_low + 1;
						else
							monster_low = monster_low - 1;

						room[monster_row][monster_low].setImageIcon(monster);
						monster_room[monster_row][monster_low] = 3;
						room[monster_row][monster_low].repaint();

					}
					sleep(200);

				} catch (InterruptedException e) {
					return;
				}

			}

		}
	}

	class ImageLabel extends JLabel {
		private ImageIcon img;

		public void setImageIcon(ImageIcon img) {
			this.img = img;
		}

		@Override
		protected void paintComponent(Graphics g) {
			if (img != null) {
				g.drawImage(img.getImage(), 0, 0, this.getWidth(), this.getHeight(), this);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (monster_room[row][col] == 3) {
			for (int i = 0; i < N; i++)
				for (int j = 0; j < N; j++)
					monster_room[i][j] = 0;
			gamemode = 1;
			JOptionPane.showMessageDialog(null, "  Game over ", "게임오버", JOptionPane.INFORMATION_MESSAGE);
			status.setText("메뉴화면");
			center_status.repaint();
			mainpaint();
			center_status.updateUI();
		}
	}

}