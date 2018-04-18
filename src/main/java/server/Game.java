package server;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;

public class Game {
    private int[][] gameField;
    Figure figure;
    private int points;
    private boolean newRecord = false;

    public int[][] getGameField() {
        return gameField;
    }

    public void setGameField(int[][] gameField) {
        this.gameField = gameField;
        points = 0;
    }

    public boolean makeMove(int x, int y) {

        //Thread.sleep(1000);
        Point[] point = this.figure.getCurrentPoints();

        if(this.figure.move(x,y,gameField))
        {
            for (Point aPoint : point) {
                gameField[aPoint.x][aPoint.y] = 0;
            }
            point = this.figure.getCurrentPoints();
            for (Point aPoint : point) {
                gameField[aPoint.x][aPoint.y] = 1;
            }
            return true;
        } else
        {
            return false;
        }

    }

    public boolean generationFigure() throws IOException {
        int figure;
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
            for (Point aPoint : point) {
                gameField[aPoint.x][aPoint.y] = 1;
            }
            return true;
        } else
        {
            setRecord();
            return false;
        }
    }

    public int checkForDeleteLine() {
        Point[] point = this.figure.getCurrentPoints();
                int deletedLines = 0;
        for (Point aPoint : point) {
            int count = 0;
            for (int j = 0; j < gameField[aPoint.x].length; j++) {
                if (gameField[aPoint.x][j] == 2) {
                    count++;
                }
            }
            if (count == gameField[aPoint.x].length) {
                setThrees(aPoint.x);
                deleteLine(aPoint.x);
                points += 100;
                deletedLines++;
            }
        }

    return deletedLines;
    }

    public void setAllTwo() {
        Point[] point = this.figure.getCurrentPoints();
        for (Point aPoint : point) {
            gameField[aPoint.x][aPoint.y] = 2;
        }
    }

    public boolean turn90() {
        Point[] point = this.figure.getCurrentPoints();

        if(this.figure.turn90(gameField))
        {
            for (Point aPoint : point) {
                gameField[aPoint.x][aPoint.y] = 0;
            }
            point = this.figure.getCurrentPoints();
            for (Point aPoint : point) {
                gameField[aPoint.x][aPoint.y] = 1;

            }
            return true;
        } else
        {
            return false;
        }
    }

    public Point[] getCurrentPoints() {
        return figure.getCurrentPoints();
    }

    public boolean isNewRecord() {
        return newRecord;
    }

    public int getCurrentPoint() {
        return points;
    }

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

    public void setAllThreeToTwo() {
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
