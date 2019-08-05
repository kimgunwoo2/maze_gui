package ½Ç½À;

import java.util.Stack;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class pos {
	pos(int x, int y) {
		this.x = x;
		this.y = y;
	}

	int x;

	int y;
}

public class maze {

	Stack<pos> st = new Stack<pos>();
	int board[][];

	

	public boolean checkboard(int b[][], int x, int y) {

		if (b[x - 1][y] != 1)
			return false;
		if (b[x + 1][y] != 1)
			return false;
		if (b[x][y - 1] != 1)
			return false;
		if (b[x][y + 1] != 1)
			return false;

		return true;

	}

	public void find(int board[][], int i, int j, int N) {
		st.removeAllElements();
		
		
		st.add(new pos(i, j));
		board[i][j] = 2;
		while (i != N - 2 || j != N - 2) {
			if (board[i][j + 1] == 0) {
				j = j + 1;
				board[i][j] = 2;
				st.add(new pos(i, j));
			} else if (board[i][j - 1] == 0) {
				j = j - 1;
				board[i][j] = 2;
				st.add(new pos(i, j));
			} else if (board[i + 1][j] == 0) {
				i = i + 1;
				board[i][j] = 2;
				st.add(new pos(i, j));
			} else if (board[i - 1][j] == 0) {
				i = i - 1;
				board[i][j] = 2;
				st.add(new pos(i, j));
			} else {
				pos temp = new pos(0, 0);
				temp = st.pop();
				i = temp.x;
				j = temp.y;

			}
		}
		st.add(new pos(N - 1, N - 2));
		
	}

	public void setroad(int board[][], int x, int y, int N) {

		List<Integer> list = new ArrayList<Integer>();
		for (int i = 1; i < 5; i++)
			list.add(i);
		Collections.shuffle(list);

		for (int i = 0; i < 4; i++) {
			if ((y + 2 <= N - 2) && (list.get(i) == 1)) {
				if (checkboard(board, x, y + 2)) {
					board[x][y + 1] = 0;
					setroad(board, x, y + 2, N);
				}
			}
			if ((y - 2 >= 1) && (list.get(i) == 2)) {
				if (checkboard(board, x, y - 2)) {

					board[x][y - 1] = 0;
					setroad(board, x, y - 2, N);
				}
			}
			if ((x - 2 >= 1) && (list.get(i) == 3)) {

				if (checkboard(board, x - 2, y)) {

					board[x - 1][y] = 0;
					setroad(board, x - 2, y, N);

				}
			}
			if ((x + 2 <= N - 2) && (list.get(i) == 4)) {

				if (checkboard(board, x + 2, y)) {

					board[x + 1][y] = 0;
					setroad(board, x + 2, y, N);

				}
			}
		}
	}

	void start(int N) {

		board = new int[N][N];

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (i % 2 == 0)
					board[i][j] = 1;
				if ((i % 2) == 1 && (j % 2) == 1)
					board[i][j] = 0;
				if ((i % 2) == 1 && (j % 2) == 0)
					board[i][j] = 1;
			}
		}

		setroad(board, 1, 1, N);
	
		board[N - 1][N - 2] = 0;
		
		
		
	}

}