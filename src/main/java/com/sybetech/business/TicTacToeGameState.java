package com.sybetech.business;
import com.mongodb.DB;
import com.mongodb.MongoClient;

import org.jongo.FindOne;
import org.jongo.Jongo;
import org.jongo.MongoCollection;

public class TicTacToeGameState {

    static final String DB_NAME = "tic-tac-toe-db";
    static final String COLLECTION_NAME = "tic-tac-toe-collection";
    private MongoCollection mongoCollection; // create-read-update-delete

    public TicTacToeGameState() {
        // use new Jongo (MongoClient().getDB).getCollection to initialize mongoCollection
    	mongoCollection = new Jongo (new MongoClient().getDB(DB_NAME)).getCollection(COLLECTION_NAME);
    }

    public boolean save(TicTacToeGameMove move) {
    	try {
    		getMongoCollection().save(move);
		} catch (Exception e) {
			return false;
		}
        return true;
    }

    public boolean clear() {
    	try {
    		getMongoCollection().drop();
		} catch (Exception e) {
			return false;
		}
        return true;
    }

    public TicTacToeGameMove findById(int id) {
    	FindOne dbEntry = getMongoCollection().findOne("{_id:#}", id);
        return dbEntry.as(TicTacToeGameMove.class);//jongo.org;
    }

    public MongoCollection getMongoCollection() {
        return mongoCollection;
    }
}
