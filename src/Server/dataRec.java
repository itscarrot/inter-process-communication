package Server;

public  class  dataRec{
	private final int		IDLE_MODE = 0;		//idle mode
	private final int		READ_MODE = 1;		//read mode
	private final int		WRITE_MODE = 2;	//write mode
	private final int		READ_TIME = 3000;	//read process time
	private final int		WRITE_TIME = 3000;	//write process time
	
	private int	state;		//IDLE, READ_MODE or WRITE_MODE
	private int 	key_id;		//primary key of the dataRec
	private int	value;		//value of the data
	/* constructor */
	public dataRec(int i_keyId, int i_value){	
		key_id = i_keyId;
		state = IDLE_MODE;
		value = i_value;
	}
	
	
	/* 
		read operation
		synchronized - mutual exclusive
	*/
	public synchronized int op_read(){
		int	out_value;
		
		/*
			op		state	action
			-------------------------------
			READ		IDLE	-> process
			READ		READ	-> block
			READ		WRITE	-> block
		*/
		// if state is not WRITE_MODE, reading data is allow
		while (state == WRITE_MODE){
			try {
				wait();
			}catch (InterruptedException e){ }
		}
		
        state = READ_MODE;

		/*
			simulate read process time
		*/
		try {
        Thread.sleep(READ_TIME);
    	}catch (InterruptedException e){
        //We've been interrupted: no more messages.
		}

		out_value = value;	//read value
		state = IDLE_MODE;
		
		notifyAll();		//wake up all threads waiting for state to change
		
		return (out_value);
	}	
	
	
	/* 
		write operation
		synchronized - mutual exclusive
	*/
	
	public synchronized boolean op_write(int i_value) {
		/*
			op		state	action
			-------------------------------
			WRITE		IDLE	-> process
			WRITE		READ	-> block
			WRITE		WRITE	-> block
		*/

		while (state != IDLE_MODE){
			try {
				wait();
			}catch (InterruptedException e){ }
		}

		/*
			simulate write process time
		*/
        state = WRITE_MODE;   
		try {
        Thread.sleep(WRITE_TIME);
    	}catch (InterruptedException e){
        //We've been interrupted: no more messages.
		}


		value = i_value;	//write value
		state = IDLE_MODE;
		notifyAll();		//wake up all threads waiting for state to change
        return true;
	}
    
    
    /*
    	get the primary key value
    */
    public int get_KeyId(){
    	return (key_id);
    }
}
