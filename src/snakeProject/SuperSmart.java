package snakeProject;

import java.util.ArrayList;

/**
 * This class represents a type of snake, one that chooses its next direction very intelligently.
 * Being a snake, the thing it must do is implement a method to calculate the next
 * direction the head should take. In this case, this is done in a very intelligent way, which is
 * explained in more detail below.
 * 
 * But it is based on using a function (fillGridDir) that determines which direction to
 * take at a moment in order to reach a certain target position the fastest way possible, if the position is free.
 * 
 * Having said that, even though this snake is not as good as I would like, having a function that tells how to get as
 * fast as possible to a target, changing the way of thinking of the snake would just be changing the choice of target
 * and a few other conditions.
 * 
 * There is also a recursive function defined in this class which tells if by choosing a certain direction at the current
 * moment the snake will surely die within a next certain number of plays (chosen by the function user) or if
 * there is at least one combination of plays that allows to survive that number of plays.
 * This last function could be better used with more time but still improves the intelligence of the snake
 * 
 * @author miguel dias
 *
 */
public class SuperSmart extends Snake {
	/** grid is a matrix representation of the map: true represents an occupied position and false a free one
	 * 
	 * Warning!! grid is not be changed in this class, because if that was the case we would
	 * be changing the map itself!
	 */
	private boolean[][] grid;
	
	/** gridDir is also a matrix representing the map. By accessing one of its positions we
	 * get the direction we should take in order to eventually get to that target position if it is free!
	 * Filling this matrix is the responsibility of the function fillGridDir, defined in this class */
	private Position [][] gridDir;
	
	/** This attribute is necessary because a very intelligent snake takes into consideration
	 * the position and direction of its opponent. */
	private Snake oponent;
		
	
	
	
	public SuperSmart(Position initialPosition, Position initialDirection, boolean[][] grid, Snake oponent) {
		super(initialPosition, initialDirection);
		this.grid=grid;
		this.oponent=oponent;
		//gridCopy = new boolean [Game.GRID_SIZE][Game.GRID_SIZE];
		//gridCopy2=new boolean [Game.GRID_SIZE][Game.GRID_SIZE];
		gridDir = new Position [Game.GRID_SIZE][Game.GRID_SIZE];
	}
	
	
	
	
	@SuppressWarnings("unused")
	public Position calcNewHeadDirection() {
		/** We need copies of the map so that we can change their fields without changing the
		 * actual matrix that represents the map of the game and its occupation!
		 * They are copies of the map in which we will work to decide the direction to take */
		boolean[][] gridCopy=new boolean [Game.GRID_SIZE][Game.GRID_SIZE];
		boolean[][] gridCopy2=new boolean [Game.GRID_SIZE][Game.GRID_SIZE];
		gridDir = new Position [Game.GRID_SIZE][Game.GRID_SIZE];
		for(int i=0; i<Game.GRID_SIZE;i++) {
			for(int j=0; j<Game.GRID_SIZE;j++) {
				gridCopy[i][j]=grid[i][j];
				gridCopy2[i][j]=grid[i][j];
			}
		}
		
		/** auxiliary variables that will allow to know in which quarter of the map the opponent is and
		 * if it is close to a corner or not. */
		int halfQuarterSize=(int)(Game.GRID_SIZE/8);
		int quarterSize=(int)(Game.ELEMENT_SIZE/4);
		
		/** auxiliary variables to use in the process of deciding the next position */
		int xtarget=0;
		int ytarget=0;
		
		/** the coordinates of the final direction chosen */
		int xReturn;
		int yReturn;

		/** auxiliary coordinates that will allow to take into account the current position and
		 * direction of the opponent in order to choose according to that
		 */
		int xOppon=oponent.getHead().getX();
		int yOppon=oponent.getHead().getY();
		int xOppDir=oponent.getHeadDir().getX();
		int yOppDir=oponent.getHeadDir().getY();

		ArrayList<Position> chooseDir = new ArrayList<Position>();		
		/** chooseDir will get the possible directions to take at the moment:
		 * the ones that the snake can take without loosing */
		chooseDir=getPossibilities(head,headDirection,gridCopy);
		
		/** if loosing is inevitable, then we can just keep the current direction. */
		if(chooseDir.size()==0) {
			return headDirection;
		}
		/**if there is only one option, then we have to take that option. */
		if(chooseDir.size()==1) {
			return chooseDir.get(0);
		}
		
		/** After calling fillGridDir, the matrix gridDir will contain in each position the direction
		 * to take now in order to get there the fastest way possible if it is free.
		 * 
		 * Warning! If the position is not accessible an exception will be raised.
		 * It is caught with a try catch. */
		fillGridDir(chooseDir,gridCopy);
		
		/**
		 * In case the snake is facing a wall it can only choose 2 options. It should always choose
		 * the direction that leads to a more central position instead of one that leads to getting
		 * closer to the corner.
		 */
		//if facing a vertical wall
		if((head.getX()==0||head.getX()==Game.GRID_SIZE-1)&&headDirection.getY()==0){
			if(head.getY()<(int)(Game.GRID_SIZE/2)) {
				//if in the upper side wants to go down
				if(!grid[head.getX()][head.getY()+1]){
					return new Position(0,1);
				}
				return new Position(0,-1);
			}
			else {
				//if in the down side wants to go up
				if(!grid[head.getX()][head.getY()-1]){
				return new Position(0,-1);
			}
			return new Position(0,1);
		}
			}
		
		//facing a horizontal wall
		if((head.getY()==0||head.getY()==Game.GRID_SIZE-1)&&headDirection.getX()==0){
			if(head.getX()<(int)(Game.GRID_SIZE/2)) {
				//if in the left side wants to go right
				if(!grid[head.getX()+1][head.getY()]){
					return new Position(1,0);
				}
				return new Position(-1,0);
			}
			else {
				//if in the right side wants to go left
				if(!grid[head.getX()-1][head.getY()]){
				return new Position(-1,0);
			}
			return new Position(-1,0);
		}
			}
		
		/**
		 * getDir will return a target position in order to trap the opponent if he is very close 
		 * to a corner. If that position is occupied the direction returned is (-1,-1), which is
		 * not to be considered. A free position can always raise an exception if it is not accessible*/
		Position toReturn = getDir(xOppon,yOppon,halfQuarterSize,quarterSize,gridCopy2);
		if(toReturn.getX()!=-1&&toReturn.getY()!=-1) {
			//exception raised if the target is not accessible
			try {
				xReturn = gridDir[xtarget][ytarget].getX();
				yReturn = gridDir[xtarget][ytarget].getY();
				return new Position(xReturn,yReturn);
		} catch (java.lang.NullPointerException exc) {		
				//no need to anything. No values will be returned if the exception was caught
			}
		}
		
		/**
		 * We choose to go near the opponent but trying to always win the inside, in order to
		 * eventually trap the opponent in a corner.
		 */
		if(yOppon<=Game.ELEMENT_SIZE/2) {
			ytarget=yOppon+3;
		} else {
			ytarget=yOppon-3;			
		}
		
		if(xOppon<=Game.ELEMENT_SIZE/2) {
			xtarget=xOppon+3;
		} else {
			xtarget=xOppon-3;			
		}
		
		for(int j=0;j<3;j++) {
		for(int aux=0;aux<4;aux++) {
			if(yOppDir==0) {
				xtarget+=xOppDir;
			}
			if(xOppDir==0) {
				ytarget+=yOppDir;
			}
			try {
				xReturn = gridDir[xtarget][ytarget].getX();
				yReturn = gridDir[xtarget][ytarget].getY();
				return new Position(xReturn,yReturn);
				// If an exception is caught it needs no treatment, no position will be returned
		} catch (java.lang.NullPointerException exc) {
		} catch (java.lang.ArrayIndexOutOfBoundsException exc) {
		}
			
		}
		}
		
		int cases = chooseDir.size();
		if(cases==1) {
			return chooseDir.get(0);
		}
		
		if(cases==2||cases==3) {
		for(int i=1;i<30;i++) {
			if(chooseDir.size()==1) {
				return chooseDir.get(0);
			}
			if(!numberFromRoot(head, chooseDir.get(0), i, grid)) {
				chooseDir.remove(chooseDir.get(0));
			}
		}
		return chooseDir.get(0);
		}	
	return headDirection;
	}
		
	/**
	 * getDir is a function that based on the opponent position and direction chooses the
	 * target position to reach in order to try and trap the opponent if he is close to one of the corners.
	 * If that position is occupied the direction returned is (-1,-1), which is not to be considered!
	 */
	public Position getDir(int xOpp,int yOpp,int quarter,int octeto,boolean[][] auxGrid) {
		/**Each corner is divided in 2 triangles */
		//if (-1,-1) is returned, the return of this function should be ignored!
		int xtarget=-1;
		int ytarget=-1;
		
		//if opponent is very up in the map
		if(yOpp<octeto) {
			
			if(xOpp==yOpp) {
				if(!auxGrid[0][0]) {
					return new Position(0,0);
				}
			}
						
			if(Game.GRID_SIZE-1-xOpp==yOpp) {
				xtarget=Game.GRID_SIZE-1;
				ytarget=0;
				if(!auxGrid[xtarget][ytarget]) {
					return new Position(xtarget,ytarget);
				}
			}
			
			//Triangle 1
			if(xtarget<ytarget&&xtarget<quarter) {
				xtarget=0;
				ytarget=yOpp+3;
				for(int j=0;j<3;j++) {
				for(int aux=0;aux<4;aux++) {
					if(!auxGrid[xtarget+j][ytarget+aux]) {
						return new Position(xtarget,ytarget);
					}
				}
				}	
		}
			
			//Triangle 2
			if(xtarget>ytarget&&xtarget<quarter) {
				ytarget=0;
				xtarget=xOpp+3;
				for(int j=0;j<3;j++) {
				for(int aux=0;aux<4;aux++) {
					if(!auxGrid[xtarget+aux][ytarget+j]) {
						return new Position(xtarget,ytarget);
					}
				}
				}
			}
		
			//Triangle 3
			if((Game.GRID_SIZE-xtarget)>ytarget&&xtarget>3*quarter) {
				ytarget=0;
				xtarget=xOpp-3;
				for(int j=0;j<3;j++) {
				for(int aux=0;aux<4;aux++) {
					if(!auxGrid[xtarget-aux][ytarget+j]) {
						return new Position(xtarget-aux,ytarget+j);
					}
				}
				}	
			}
			
			//Triangle 4
			if((Game.GRID_SIZE-xtarget)<ytarget&&xtarget>3*quarter) {
				ytarget=yOpp+3;
				xtarget=Game.GRID_SIZE-1;
				for(int j=0;j<3;j++) {
				for(int aux=0;aux<4;aux++) {
					if(!auxGrid[xtarget-j][ytarget+aux]) {
						return new Position(xtarget-j,ytarget+aux);
					}
				}
				}
			}
			
		}
		
		//if the opponent snake is very down
		if(yOpp>3*octeto) {
			
			if(xOpp==yOpp) {
				xtarget=Game.GRID_SIZE-1;
				ytarget=Game.GRID_SIZE-1;
				if(!auxGrid[xtarget][ytarget]) {
					return new Position(xtarget,ytarget);
				}
			}
			
			if(xOpp==Game.GRID_SIZE-1-yOpp) {
				xtarget=0;
				ytarget=Game.GRID_SIZE-1;
				if(!auxGrid[xtarget][ytarget]) {
					return new Position(xtarget,ytarget);
				}
			}

			//Triangle 5
			if(xtarget<(Game.GRID_SIZE-ytarget)&&xtarget<quarter) {
				xtarget=0;
				ytarget=yOpp-3;
				for(int j=0;j<3;j++) {
				for(int aux=0;aux<4;aux++) {
					if(!auxGrid[xtarget+j][ytarget-aux]) {
						return new Position(xtarget,ytarget);
					}
				}
				}
		}
			
			//Triangle 6
			if(xtarget>(Game.GRID_SIZE-ytarget)&&xtarget<quarter) {
				ytarget=Game.GRID_SIZE-1;
				xtarget=xOpp+3;
				for(int j=0;j<3;j++) {
					for(int aux=0;aux<4;aux++) {
						if(!auxGrid[xtarget+aux][ytarget-j]) {
							return new Position(xtarget,ytarget);
						}
			}
				}
			}
			
			//Triangle 7
			if((Game.GRID_SIZE-xtarget)>(Game.GRID_SIZE-ytarget)&&xtarget>3*quarter&&ytarget>3*ytarget) {
				ytarget=Game.GRID_SIZE-1;
				xtarget=xOpp-3;
				for(int j=0;j<3;j++) {
				for(int aux=0;aux<4;aux++) {
					if(!auxGrid[xtarget-aux][ytarget-j]) {
						return new Position(xtarget,ytarget);
					}
				}
				}
				}
			
			//Triangle 8
			if((Game.GRID_SIZE-xtarget)<(Game.GRID_SIZE-ytarget)&&xtarget>3*quarter) {
				ytarget=yOpp-3;
				xtarget=Game.GRID_SIZE-1;
				for(int j=0;j<3;j++) {
				for(int aux=0;aux<4;aux++) {
					if(!auxGrid[xtarget-j][ytarget-aux]) {
						return new Position(xtarget,ytarget);
					}
				}
				}
			}
		}
		return new Position(-1,-1);
	}
	
	/**
	 * This function tests if the position to which head will arrive if it takes the direction
	 * headDirection is free (return true) or occupied (return false).
	 */
	public boolean nextIsFree(Position head, Position headDirection, boolean[][] grid) {
		//an exception can be caught if the next position is outside the limits of the map (return false)
		try{
			//in the grid, true means occupied and false means free
			if(!grid[head.getX()+headDirection.getX()][head.getY()+headDirection.getY()]) {
				return true;
			} else {
				return false;
			}
		} catch(ArrayIndexOutOfBoundsException exc) {
			return false;
		}

	}
	
	/**
	 * This function returns a list with the possible directions that the head can take with its current
	 * direction: the ones that the snake can take without loosing immediately
	 */
	public ArrayList<Position> getPossibilities(Position head, Position headDirection, boolean[][] grid){
		ArrayList<Position> chooseDirFrom = new ArrayList<Position>();
		ArrayList<Position> listOfPossibleDir = new ArrayList<Position>();
		
		//We start by adding the 3 possible directions we can take from the current direction
		listOfPossibleDir.add(new Position(headDirection.getX(), headDirection.getY()));
		//if currently vertical, can go right or left
		if(headDirection.getX()==0) {
			listOfPossibleDir.add(new Position(1, 0));
			listOfPossibleDir.add(new Position(-1, 0));
		}
		//if currently horizontal, can go down or up
		else if(headDirection.getY()==0) {
			listOfPossibleDir.add(new Position(0, 1));
			listOfPossibleDir.add(new Position(0, -1));
		}

		//Of the possible directions we only want to save the ones that lead to free positions
		for(Position dir : listOfPossibleDir) {
				if(nextIsFree(head,dir,grid)) {
					chooseDirFrom.add(dir);
				}
		}
		
		return chooseDirFrom;
	}
	
	/** This function fills gridDir in such a way that: gridDir will have in a position the direction
	 * to take in order to get to that position the fastest way possible if it is free.
	 * 
	 * Warning! If the position is not accessible, if after this function another function tries to
	 * access that position it is ok, it's reference does exist.
	 * However, accessing the parameters of that position will raise an exception.
	 * @param chooseDir: the possible current directions, which means that the accessible
	 * 		  positions will demand one between these directions to get there. */
	public void fillGridDir(ArrayList<Position> chooseDir,boolean[][] gridCopy) {
		/**to know while we are still filling the map with possible plays */
		boolean fillingVirtualMap=true;
		/** an auxiliar list to store at each step of a loop the possible directions to take */
	ArrayList<Position> auxChooseDir = new ArrayList<Position>();
	
	/**Each component of this list is a vector of dimension 3 that stores:
	 * 0: the position we are considerating at each step of a loop,
	 * 1: the direction with which we just arrived there
	 * 2: the initial direction from which we started to get there*/
	ArrayList<Position[]> listOfDirs = new ArrayList<Position[]>();
	//auxList beacuse we need an auxiliary list to save the new data when we are in a new loop
	ArrayList<Position[]> auxList;

	//fill listOfDirs with the possible initial directions to take
	for(Position dir : chooseDir) {
		Position[] auxPos = new Position[] {head,dir,dir};
		listOfDirs.add(auxPos);
	}
	
	//as long as we can still move somewhere else in the virtual map we continue the loop
	while(fillingVirtualMap) {
		auxList = new ArrayList<Position[]>();
		for(Position[] list : listOfDirs) {
			Position currentPos = list[0];
			Position currentDir = list[1];
			Position initialDir = list[2];
			Position nextPos = new Position(currentPos.getX()+currentDir.getX(), currentPos.getY()+currentDir.getY());
			
			//if a position is occupied, we do not consider going there
			if(gridCopy[nextPos.getX()][nextPos.getY()]) {
				continue;
			}
			
			//The way to get to the position is to take the initial direction that led there!
			gridDir[nextPos.getX()][nextPos.getY()]=initialDir;
			//and we need to occupy that position in the virtual grid
			gridCopy[nextPos.getX()][nextPos.getY()]=true;
			
			/*from the next position there will be new possible directions to take:
			 * we repeat the process from there. Thats why we will use an auxiliary list */
			auxChooseDir = getPossibilities(nextPos,currentDir,gridCopy);

			for(Position dir : auxChooseDir) {
				Position[] auxPos = new Position[] {nextPos,dir,initialDir};
				auxList.add(auxPos);
			}
		}
		/*When we repeat the loop, it is listOfDirs that must contain the information that at the moment
		 * is in auxList. And auxList will be a new clear list in the beginning of the next iteration */
		listOfDirs=auxList;

		//When there is no more possible positions to go to, the loop can finish
		if(listOfDirs.size()==0) {
			fillingVirtualMap=false;
		}
	}
	}

	/**
	 * This function receives a current head position and direction and returns if from any possible
	 * combination of choices based on the current configuration of the map it is possible or not to
	 * do "numberOfPlaysToPreview" plays without loosing.
	 * 
	 * @param head, headDirection and numberOfPlaysToPreview are what the name indicates
	 * @param gridAux needs to be an auxiliary grid and not the real one because it we will be changed
	 * @return true if we can do numberOfPlaysToPreview plays without loosing
	 */
	public boolean numberFromRoot(Position head, Position headDirection, int numberOfPlaysToPreview, boolean[][] grid) {
		boolean[][] gridAux = new boolean[Game.GRID_SIZE][Game.GRID_SIZE];
		for(int i=0; i<Game.GRID_SIZE;i++) {
			for(int j=0; j<Game.GRID_SIZE;j++) {
				gridAux[i][j]=grid[i][j];
			}
		}
		
		ArrayList<Position> chooseDir = new ArrayList<Position>();
		chooseDir=getPossibilities(head,headDirection,gridAux);
		
		if(chooseDir.size()==0) {
			return false;
		}

			//if at the moment it is ok then we have one less play to predict
			numberOfPlaysToPreview--;
			if(numberOfPlaysToPreview==0) {
				return true;
			}
			
			for(Position dir : chooseDir) {
				//for each possibility I see the new possibilities and call the function recursively
					Position nextPos = new Position(head.getX()+dir.getX(), head.getY()+dir.getY());
					gridAux[nextPos.getX()][nextPos.getY()]=true;
					
					/*it is enough that one possibility can go all the way until the end to say that it
					possible to do the number of plays asked */
					if(numberFromRoot(nextPos,dir,numberOfPlaysToPreview,gridAux)){
						return true;
					}
			}
			//there was no good way!
			return false;
	}
}
