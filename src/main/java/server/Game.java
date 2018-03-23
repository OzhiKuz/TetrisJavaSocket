package server;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Properties;
import java.util.Random;

public class Game implements GameI {
    int[][] gameField;
    Figure figure;
    int points;
    boolean newRecord = false;

    public int[][] getGameField() {
        return gameField;
    }

    public void setGameField(int[][] gameField) {
        this.gameField = gameField;
        points = 0;
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
    public boolean generationFigure() throws IOException {
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
        } else
        {
            setRecord();
            return false;
        }
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
                setThrees(point[i].x);
                deleteLine(point[i].x);
                points += 100;
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
        Point[] point = this.figure.getCurrentPoints();

        if(this.figure.turn90(gameField))
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
    public Point[] getCurrentPoints() throws RemoteException {
        return figure.getCurrentPoints();
    }

    @Override
    public boolean isNewRecord() throws RemoteException {
        return newRecord;
    }

    @Override
    public int getCurrentPoint() throws RemoteException {
        return points;
    }

    @Override
    public int getRecord() throws IOException {
        Properties prop = new Properties();
        FileInputStream fileInputStream = new FileInputStream("src/main/resources/records.properties");
        int record = 0;
        prop.load(fileInputStream);
        if (gameField != null)
        record = Integer.valueOf(prop.getProperty("" + gameField[0].length + ""));
        fileInputStream.close();
        return record;
    }

    @Override
    public void setAllThreeToTwo() throws IOException {
        for (int i = 0; i < gameField.length; i++)
        {
            for (int j = 0; j < gameField[i].length; j++)
            {
                if (gameField[i][j] == 3)
                    gameField[i][j] = 2;
            }
        }
    }

    private void setThrees(int x)
    {
        for (int i = 0; i < x; i++)
        {
            for (int j = 0; j < gameField[i].length; j++)
            {
                if (gameField[i][j] == 2)
                    gameField[i][j] = 3;
            }
        }
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

    private void setRecord() throws IOException {
        Properties prop = new Properties();
        FileInputStream fileInputStream = new FileInputStream("src/main/resources/records.properties");

        prop.load(fileInputStream);
        int record = Integer.valueOf(prop.getProperty("" + gameField[0].length + ""));
        fileInputStream.close();

        if (points > record)
        {
            newRecord = true;
            FileOutputStream fileOutputStream = new FileOutputStream("src/main/resources/records.properties");
            prop.setProperty("" + gameField[0].length + "", String.valueOf(points));
            prop.store(fileOutputStream,null);
            fileOutputStream.close();
        }
    }


}
