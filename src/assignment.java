import com.sun.xml.internal.bind.v2.TODO;
import sun.misc.Queue;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;

enum insides {
    Harry,Filch,Book,Cloak,Norris,Exit,inspectorZone
}
class Coordinates{
    int x,y;
    Coordinates(){
        x=0;y=0;
    }
    Coordinates(int x,int y){
        this.x=x;
        this.y=y;
    }
    int getX(){
        return x;
    }
    int getY(){
        return y;
    }
}
public class assignment {
    public static void main(String[] args) {

        Game igra= new Game();
        igra.setGame(new Coordinates(0,0),new Coordinates(4,2),new Coordinates(2,7),new Coordinates(7,4),new Coordinates(0,8),new Coordinates(1,4));
        //igra.printGame();
        //igra.randomGenerateGame();
        igra.printGame();
        BackTracking alg = new BackTracking(igra,1);

   }
}

class Game {
    cell[][] space = new cell[9][9];
    Coordinates Filch = new Coordinates();
    Coordinates Norris = new Coordinates();
    Coordinates Book = new Coordinates();
    Coordinates Exit = new Coordinates();
    Coordinates Cloak = new Coordinates();
    Coordinates Harry = new Coordinates();

    Game(){
        for (int i=0;i<9;i++){
            for (int j=0;j<9;j++){
                space[i][j]=new cell();
            }
        }
    }

    boolean setGame(Coordinates H,Coordinates F,Coordinates N,Coordinates B,Coordinates C,Coordinates E){
        if (((Math.abs(B.getX()-F.getX())<3)&&(Math.abs(B.getY()-F.getY())<3))||((Math.abs(B.getX()-N.getX())<2)&&(Math.abs(B.getY()-N.getY())<2))){
            return false;
        }
        if (((Math.abs(C.getX()-F.getX())<3)&&(Math.abs(C.getY()-F.getY())<3))||((Math.abs(C.getX()-N.getX())<2)&&(Math.abs(C.getY()-N.getY())<2))){
            return false;
        }
        if (((Math.abs(E.getX()-F.getX())<3)&&(Math.abs(E.getY()-F.getY())<3))||((Math.abs(E.getX()-N.getX())<2)&&(Math.abs(E.getY()-N.getY())<2))){
            return false;
        }
        if (E.getX()==B.getX()&&E.getY()==B.getY()){
            return false;
        }
        Filch=F;
        Norris=N;
        Harry=H;
        Book=B;
        Cloak=C;
        Exit=E;
        prepareMap();
        return true;
    }
    /**
    * This method prepares the map for the algorithm, setting the environment in 2d array of ints
    *
    * */
    void prepareMap(){
        for (int i=0;i<9;i++){
            for (int j=0;j<9;j++){
                space[i][j].prepare();
            }
        }
        space[Harry.getX()][Harry.getY()].addInCell(insides.Harry);
        space[Filch.getX()][Filch.getY()].addInCell(insides.Filch);
        for (int i= Math.max(Filch.getX()-2,0);i< Math.min(Filch.getX()+3,9);i++){
            for (int j= Math.max(Filch.getY()-2,0);j< Math.min(Filch.getY()+3,9);j++){
                space[i][j].inspected=true;
            }
        }
        space[Norris.getX()][Norris.getY()].addInCell(insides.Norris);
        for (int i= Math.max(Norris.getX()-1,0);i<Math.min(Norris.getX()+2,9) ;i++){
            for (int j=Math.max(Norris.getY()-1,0) ;j<Math.min( Norris.getY()+2,9);j++){
                space[i][j].inspected=true;
            }
        }
        space[Book.getX()][Book.getY()].addInCell(insides.Book);
        space[Cloak.getX()][Cloak.getY()].addInCell(insides.Cloak);
        space[Exit.getX()][Exit.getY()].addInCell(insides.Exit);
    }
    void randomGenerateGame(){
        boolean generate=true;
        while(generate) {
            Coordinates F = new Coordinates((int) (Math.random() * 9), (int) (Math.random() * 9));
            Coordinates N = new Coordinates((int) (Math.random() * 9), (int) (Math.random() * 9));
            Coordinates B = new Coordinates((int) (Math.random() * 9), (int) (Math.random() * 9));
            Coordinates C = new Coordinates((int) (Math.random() * 9), (int) (Math.random() * 9));
            Coordinates E = new Coordinates((int) (Math.random() * 9), (int) (Math.random() * 9));
            Coordinates H = new Coordinates(0, 0);
            generate=!setGame(H,F,N,B,C,E);
        }
    }
    void decreasePerception(){
        for (int i= Math.max(Norris.getX()-1,0);i<Math.min(Norris.getX()+2,9) ;i++){
            for (int j=Math.max(Norris.getY()-1,0) ;j<Math.min( Norris.getY()+2,9);j++){
                space[i][j].inspected=false;
                if (i==Norris.getX()&&j==Norris.getY()){
                    space[i][j].inspected=true;
                }
            }
        }
        for (int i= Math.max(Filch.getX()-2,0);i< Math.min(Filch.getX()+3,9);i++){
            for (int j= Math.max(Filch.getY()-2,0);j< Math.min(Filch.getY()+3,9);j++){
                space[i][j].inspected=false;
                if (i==Filch.getX()&&j==Filch.getY()){
                    space[i][j].inspected=true;
                }
            }
        }
    }
    void printGame(){
        for (int i=8;i>=0;i--){
            for (int j=0; j<9;j++){
                space[j][i].print();
                System.out.print(" ");
            }
            System.out.println();
        }
    }
}

class cell{
    ArrayList<String> elements= new ArrayList<>();
    boolean inspected;
    void addInCell(insides actorToAdd){
        switch (actorToAdd){
            case Book:
                elements.add("B");
                break;
            case Exit:
                elements.add("E");
                break;
            case Cloak:
                elements.add("C");
                break;
            case Filch:
                elements.add("F");
                inspected=true;
                break;
            case Harry:
                elements.add("H");
                break;
            case Norris:
                elements.add("N");
                inspected=true;
                break;
            case inspectorZone:
                inspected=true;
        }
    }
    boolean isInspected(){
        return inspected;
    }
    void print(){
        if (elements.size()==0){
            if (inspected){
                System.out.print("*");
            } else {
                System.out.print("-");
            }
        }
        for (String elem: elements){
            System.out.print(elem);
        }
    }
    void prepare(){
        elements.clear();
        inspected=false;
    }
}

enum memInsides{
    unknown,safe,inspected,
}

class MemoryCell {
    int steps;
    Coordinates previousCell;
    memInsides typeOfCell;
    boolean unvisited;
    MemoryCell(){
        steps=Integer.MAX_VALUE;
        typeOfCell=memInsides.unknown;
        unvisited=true;
    }
}
class BackTracking{
    int perception;
    Game game=new Game();
    boolean hasBook,hasCloak;
    Coordinates position= new Coordinates();
    Stack<MemoryCell> trace= new Stack<>();
    MemoryCell[][] mind=new MemoryCell[9][9];

    BackTracking(Game game,int per){
        for (int i=0;i<9;i++){
            for (int j=0; j<9;j++){
                mind[i][j]=new MemoryCell();
            }
        }
        perception=per;
        hasBook=false;
        hasCloak=false;
        this.game=game;
        position=this.game.Harry;
        mind[position.getX()][position.getY()].steps=0;
        take();
        seeCells();
        if (move(getNewCell())){
            System.out.println("smog");
        }
    }
    Coordinates getNewCell(){
        if (position.getX()<8){
            if (mind[position.getX()+1][position.getY()].typeOfCell==memInsides.safe&&mind[position.getX()+1][position.getY()].unvisited){
                return new Coordinates(position.getX()+1,position.getY());
            }
        }
        if (position.getX()<8&&position.getY()<8){
            if (mind[position.getX()+1][position.getY()+1].typeOfCell==memInsides.safe&&mind[position.getX()+1][position.getY()+1].unvisited){
                return new Coordinates(position.getX()+1,position.getY()+1);
            }
        }
        if (position.getY()<8){
            if (mind[position.getX()][position.getY()+1].typeOfCell==memInsides.safe&&mind[position.getX()][position.getY()+1].unvisited){
                return new Coordinates(position.getX(),position.getY()+1);
            }
        }
        if (position.getX()>0&&position.getY()<8){
            if (mind[position.getX()-1][position.getY()+1].typeOfCell==memInsides.safe&&mind[position.getX()-1][position.getY()+1].unvisited){
                return new Coordinates(position.getX()-1,position.getY()+1);
            }
        }
        if (position.getX()>0){
            if (mind[position.getX()-1][position.getY()].typeOfCell==memInsides.safe&&mind[position.getX()-1][position.getY()].unvisited){
                return new Coordinates(position.getX()-1,position.getY());
            }
        }
        if (position.getX()>0&&position.getY()>0){
            if (mind[position.getX()-1][position.getY()-1].typeOfCell==memInsides.safe&&mind[position.getX()-1][position.getY()-1].unvisited){
                return new Coordinates(position.getX()-1,position.getY()-1);
            }
        }
        if (position.getY()>0){
            if (mind[position.getX()][position.getY()-1].typeOfCell==memInsides.safe&&mind[position.getX()][position.getY()-1].unvisited){
                return new Coordinates(position.getX(),position.getY()-1);
            }
        }
        if (position.getX()<8&&position.getY()>0){
            if (mind[position.getX()+1][position.getY()-1].typeOfCell==memInsides.safe&&mind[position.getX()+1][position.getY()-1].unvisited){
                return new Coordinates(position.getX()+1,position.getY()-1);
            }
        }
        // when we don't know
        if (position.getX()<8){
            if (mind[position.getX()+1][position.getY()].typeOfCell==memInsides.unknown&&mind[position.getX()+1][position.getY()].unvisited){
                return new Coordinates(position.getX()+1,position.getY());
            }
        }
        if (position.getX()<8&&position.getY()<8){
            if (mind[position.getX()+1][position.getY()+1].typeOfCell==memInsides.unknown&&mind[position.getX()+1][position.getY()+1].unvisited){
                return new Coordinates(position.getX()+1,position.getY()+1);
            }
        }
        if (position.getY()<8){
            if (mind[position.getX()][position.getY()+1].typeOfCell==memInsides.unknown&&mind[position.getX()][position.getY()+1].unvisited){
                return new Coordinates(position.getX(),position.getY()+1);
            }
        }
        if (position.getX()>0&&position.getY()<8){
            if (mind[position.getX()-1][position.getY()+1].typeOfCell==memInsides.unknown&&mind[position.getX()-1][position.getY()+1].unvisited){
                return new Coordinates(position.getX()-1,position.getY()+1);
            }
        }
        if (position.getX()>0){
            if (mind[position.getX()-1][position.getY()].typeOfCell==memInsides.unknown&&mind[position.getX()-1][position.getY()].unvisited){
                return new Coordinates(position.getX()-1,position.getY());
            }
        }
        if (position.getX()>0&&position.getY()>0){
            if (mind[position.getX()-1][position.getY()-1].typeOfCell==memInsides.unknown&&mind[position.getX()-1][position.getY()-1].unvisited){
                return new Coordinates(position.getX()-1,position.getY()-1);
            }
        }
        if (position.getY()>0){
            if (mind[position.getX()][position.getY()-1].typeOfCell==memInsides.unknown&&mind[position.getX()][position.getY()-1].unvisited){
                return new Coordinates(position.getX(),position.getY()-1);
            }
        }
        if (position.getX()<8&&position.getY()>0){
            if (mind[position.getX()+1][position.getY()-1].typeOfCell==memInsides.unknown&&mind[position.getX()+1][position.getY()-1].unvisited){
                return new Coordinates(position.getX()+1,position.getY()-1);
            }
        }

        return new Coordinates(-1,-1);
    }
    // TODO: split search into finding the book and the exit
    boolean move(Coordinates cellMoveInto){
        System.out.println();
        printCells();
        if (cellMoveInto.getY()==-1&&cellMoveInto.getX()==-1){
            return false;
        }
        if (game.space[cellMoveInto.getX()][cellMoveInto.getY()].inspected){
            return false;
        }
        Coordinates previous= new Coordinates(position.getX(), position.getY());
        mind[cellMoveInto.getX()][cellMoveInto.getY()].steps=mind[position.getX()][position.getY()].steps+1;
        mind[cellMoveInto.getX()][cellMoveInto.getY()].previousCell=previous;
        position.x=cellMoveInto.getX();
        position.y=cellMoveInto.getY();
        mind[position.getX()][position.getY()].unvisited=false;
        seeCells();
        if (take()){
            return true;
        }
        while (true){
            Coordinates moveNext = getNewCell();
            if (moveNext.getX()==-1&&moveNext.getY()==-1){
                position=previous;
                break;
            } else {
                if (move(moveNext)) {
                    return true;
                }
                mind[moveNext.getX()][moveNext.getY()].unvisited=false;
            }
        }

        return false;
    }
    boolean take(){
        ArrayList<String> insides = game.space[position.getX()][position.getY()].elements;
        while (!insides.isEmpty()){
            if(insides.get(0).equals("C")){
                hasCloak=true;
                game.decreasePerception();
                // clear our mind
                for (int i=0;i<9;i++){
                    for (int j=0;j<9;j++){
                        if (mind[i][j].typeOfCell==memInsides.inspected){
                            mind[i][j].typeOfCell=memInsides.unknown;
                        }
                    }
                }
                seeCells();
            }
            if(insides.get(0).equals("B")){
                hasBook=true;
            }
            if (insides.get(0).equals("E")&&hasBook){
                return true;
            }
            insides.remove(0);
        }
        return false;
    }
    void printCells(){
        for (int i=8;i>=0;i--){
            for (int j=0;j<9;j++){
                if (position.getX()==j&&position.getY()==i){
                    System.out.print("H ");
                    continue;
                }
                if (mind[j][i].unvisited){
                    System.out.print("- ");
                } else {
                    System.out.print("* ");
                }
            }
            System.out.println();
        }
    }
    void seeCells(){
        if (perception==1) {
            if (position.getX() < 8) {
                if (game.space[position.getX()+1][position.getY()].inspected) {
                    mind[position.getX() + 1][position.getY()].typeOfCell = memInsides.inspected;
                } else {
                    mind[position.getX() + 1][position.getY()].typeOfCell = memInsides.safe;
                }
            }
            if ((position.getX() < 8) && (position.getY() < 8)) {
                if (game.space[position.getX() + 1][position.getY()+1].inspected) {
                    mind[position.getX() + 1][position.getY() + 1].typeOfCell = memInsides.inspected;
                } else {
                    mind[position.getX() + 1][position.getY() + 1].typeOfCell = memInsides.safe;
                }
            }
            if (position.getY() < 8) {
                if (game.space[position.getX()][position.getY() + 1].inspected) {
                    mind[position.getX()][position.getY() + 1].typeOfCell = memInsides.inspected;
                } else {
                    mind[position.getX()][position.getY() + 1].typeOfCell = memInsides.safe;
                }
            }
            if ((position.getX() > 0) && (position.getY() < 8)) {
                if (game.space[position.getX() - 1][position.getY()+1].inspected) {
                    mind[position.getX() - 1][position.getY() + 1].typeOfCell = memInsides.inspected;
                } else {
                    mind[position.getX() - 1][position.getY() + 1].typeOfCell = memInsides.safe;
                }
            }
            if (position.getX() > 0) {
                if (game.space[position.getX() - 1][position.getY()].inspected) {
                    mind[position.getX() - 1][position.getY()].typeOfCell = memInsides.inspected;
                } else {
                    mind[position.getX() - 1][position.getY()].typeOfCell = memInsides.safe;
                }
            }
            if ((position.getX() > 0) && (position.getY() > 0)) {
                if (game.space[position.getX() - 1][position.getY() - 1].inspected) {
                    mind[position.getX() - 1][position.getY() - 1].typeOfCell = memInsides.inspected;
                } else {
                    mind[position.getX() - 1][position.getY() - 1].typeOfCell = memInsides.safe;
                }
            }
            if (position.getY() > 0) {
                if (game.space[position.getX()][position.getY() - 1].inspected) {
                    mind[position.getX()][position.getY() - 1].typeOfCell = memInsides.inspected;
                } else {
                    mind[position.getX()][position.getY() - 1].typeOfCell = memInsides.safe;
                }
            }
            if ((position.getX() < 8) && (position.getY() > 0)) {
                if (game.space[position.getX() + 1][position.getY() - 1].inspected) {
                    mind[position.getX() + 1][position.getY() - 1].typeOfCell = memInsides.inspected;
                } else {
                    mind[position.getX() + 1][position.getY() - 1].typeOfCell = memInsides.safe;
                }
            }
        }
        if (perception==2) {
            if (position.getX() < 7) {
                if (game.space[position.getX() + 2][position.getY()].inspected) {
                    mind[position.getX() + 2][position.getY()].typeOfCell = memInsides.inspected;
                } else {
                    mind[position.getX() + 2][position.getY()].typeOfCell = memInsides.safe;
                }
            }
            if ((position.getX() < 7) && (position.getY() < 8)) {
                if (game.space[position.getX() + 2][position.getY()+1].inspected) {
                    mind[position.getX() + 2][position.getY() + 1].typeOfCell = memInsides.inspected;
                } else {
                    mind[position.getX() + 2][position.getY() + 1].typeOfCell = memInsides.safe;
                }
            }
            if ((position.getX() < 8) && (position.getY() < 7)) {
                if (game.space[position.getX() + 1][position.getY()+2].inspected) {
                    mind[position.getX() + 1][position.getY() + 2].typeOfCell = memInsides.inspected;
                } else {
                    mind[position.getX() + 1][position.getY() + 2].typeOfCell = memInsides.safe;
                }
            }
            if (position.getY() < 7) {
                if (game.space[position.getX()][position.getY() + 2].inspected) {
                    mind[position.getX()][position.getY() + 2].typeOfCell = memInsides.inspected;
                } else {
                    mind[position.getX()][position.getY() + 2].typeOfCell = memInsides.safe;
                }
            }
            if ((position.getX() > 0) && (position.getY() < 7)) {
                if (game.space[position.getX() - 1][position.getY() + 2].inspected) {
                    mind[position.getX() - 1][position.getY() + 2].typeOfCell = memInsides.inspected;
                } else {
                    mind[position.getX() - 1][position.getY() + 2].typeOfCell = memInsides.safe;
                }
            }
            if ((position.getX() > 1) && (position.getY() < 8)) {
                if (game.space[position.getX() - 2][position.getY() + 1].inspected) {
                    mind[position.getX() - 2][position.getY() + 1].typeOfCell = memInsides.inspected;
                } else {
                    mind[position.getX() - 2][position.getY() + 1].typeOfCell = memInsides.safe;
                }
            }
            if (position.getX() > 1) {
                if (game.space[position.getX() - 2][position.getY()].inspected) {
                    mind[position.getX() - 2][position.getY()].typeOfCell = memInsides.inspected;
                } else {
                    mind[position.getX() - 2][position.getY()].typeOfCell = memInsides.safe;
                }
            }
            if ((position.getX() > 1) && (position.getY() > 0)) {
                if (game.space[position.getX() - 2][position.getY() - 1].inspected) {
                    mind[position.getX() - 2][position.getY() - 1].typeOfCell = memInsides.inspected;
                } else {
                    mind[position.getX() - 2][position.getY() - 1].typeOfCell = memInsides.safe;
                }
            }
            if ((position.getX() > 0) && (position.getY() > 1)) {
                if (game.space[position.getX() - 1][position.getY() - 2].inspected) {
                    mind[position.getX() - 1][position.getY() - 2].typeOfCell = memInsides.inspected;
                } else {
                    mind[position.getX() - 1][position.getY() - 2].typeOfCell = memInsides.safe;
                }
            }
            if (position.getY() > 1) {
                if (game.space[position.getX()][position.getY() - 2].inspected) {
                    mind[position.getX()][position.getY() - 2].typeOfCell = memInsides.inspected;
                } else {
                    mind[position.getX()][position.getY() - 2].typeOfCell = memInsides.safe;
                }
            }
            if ((position.getX() < 8) && (position.getY() > 1)) {
                if (game.space[position.getX() + 1][position.getY() - 2].inspected) {
                    mind[position.getX() + 1][position.getY() - 2].typeOfCell = memInsides.inspected;
                } else {
                    mind[position.getX() + 1][position.getY() - 2].typeOfCell = memInsides.safe;
                }
            }
            if ((position.getX() < 7) && (position.getY() > 0)) {
                if (game.space[position.getX() + 2][position.getY() - 1].inspected) {
                    mind[position.getX() + 2][position.getY() - 1].typeOfCell = memInsides.inspected;
                } else {
                    mind[position.getX() + 2][position.getY() - 1].typeOfCell = memInsides.safe;
                }
            }
        }
    }
}