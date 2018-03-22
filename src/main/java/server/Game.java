package server;

import java.awt.*;
import java.rmi.RemoteException;
import java.util.Random;

public class Game implements GameI {
    int[][] gameField;
    Figure figure;

    public int[][] getGameField() {
        return gameField;
    }

    public void setGameField(int[][] gameField) {
        this.gameField = gameField;
    }

    @Override
    public boolean makeMove(int x, int y) throws RemoteException, InterruptedException {

        //Thread.sleep(1000);
        Point[] point = this.figure.getCurrentPoints();

        if(this.figure.move(x,y,gameField))
        {
            for (int i = 0; i < point.length; i++)
            {
                gameField[point[i].x][point[i].y] = 0;

            }
            point = this.figure.getCurrentPoints();
            for (int i = 0; i < point.length; i++)
            {
                gameField[point[i].x][point[i].y] = 1;

            }
            return true;
        } else
        {
            return false;
        }

    }

    @Override
    public boolean generationFigure() throws RemoteException {
        int figure = 0;
        Random rand = new Random();
        figure = rand.nextInt(7);

        switch (figure)
        {
            case 0:
            {
                this.figure = new IFigure(gameField);
                break;
            }
            case 1:
            {
                this.figure = new JFigure(gameField);
                break;
            }
            case 2:
            {
                this.figure = new LFigure(gameField);
                break;
            }
            case 3:
            {
                this.figure = new QFigure(gameField);
                break;
            }
            case 4:
            {
                this.figure = new SFigure(gameField);
                break;
            }
            case 5:
            {
                this.figure = new TFigure(gameField);
                break;
            }
            case 6:
            {
                this.figure = new ZFigure(gameField);
                break;
            }
        }
        if (this.figure.setFirst(gameField))
        {
            Point[] point = this.figure.getCurrentPoints();
            for (int i = 0; i < point.length; i++)
            {
                gameField[point[i].x][point[i].y] = 1;
            }
            return true;
        } else return false;
    }

    @Override
    public int checkForDeleteLine() throws RemoteException {
        Point[] point = this.figure.getCurrentPoints();
        int deletedLines = 0;
        for (int i = 0; i < point.length; i++)
        {
            int count = 0;
            for (int j = 0; j < gameField[point[i].x].length; j++)
            {
                if (gameField[point[i].x][j] == 2)
                {
                    count++;
                }
            }
            if (count == gameField[point[i].x].length)
            {
                deleteLine(point[i].x);
                deletedLines++;
            }
        }

    return deletedLines;
    }

    @Override
    public void setAllTwo() throws RemoteException {
        Point[] point = this.figure.getCurrentPoints();
        for (int i = 0; i < point.length; i++)
        {
            gameField[point[i].x][point[i].y] = 2;
        }
    }

    @Override
    public boolean turn90() throws RemoteException {
        return figure.turn90(gameField);
    }

    private void deleteLine(int x)
    {
        for (int i = x; i > 0; i--)
        {
            for (int j = 0; j < gameField[x].length; j++)
            {
                gameField[i][j] = gameField[i - 1][j];
            }
        }

        for (int i = 0; i < gameField[x].length; i++)
        {
            gameField[0][i] = 0;
        }
    }


}
