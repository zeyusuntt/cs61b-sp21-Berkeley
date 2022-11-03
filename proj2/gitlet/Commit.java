package gitlet;

// TODO: any imports you need here

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date; // TODO: You'll likely use this in this class
import java.util.HashMap;
import java.util.List;

/** Represents a gitlet commit object.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *
 *  @author TODO
 */
public class Commit implements Serializable {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Commit class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided one example for `message`.
     */

    /** The message of this Commit. */
    static final File COMMIT_FOLDER = Utils.join(Repository.GITLET_DIR, "commits");
    private String message;
    private Date timeStamp;
    private ArrayList<String> parent;
    private HashMap<String, String> blobs;


    /* TODO: fill in the rest of this class. */

    public String getMessage() {
        return this.message;
    }

    public Date getTimeStamp() {
        return this.timeStamp;
    }

    public ArrayList<String> getParent() {
        return parent;
    }

    public HashMap<String, String> getBlobs() {
        return blobs;
    }

    public Commit() {
        this.message = "initial commit";
        this.timeStamp = new Date(0);
        this.parent = new ArrayList<>() ;
        this.blobs = new HashMap<>();
    }

    public Commit(String m, Date d, ArrayList<String> p, HashMap<String, String> b) {
        this.message = m;
        this.timeStamp = d;
        this.parent = p;
        this.blobs = b;
    }

    public static Commit fromFile(String uid) {
        File readFile = Utils.join(COMMIT_FOLDER, uid);
        if (!readFile.exists()) {
            throw new GitletException("No commit with that id exists.");
        }
        return Utils.readObject(readFile, Commit.class);
    }

    public void saveCommit(){
        String uid = Utils.sha1(Utils.serialize(this));
        File saveFile = Utils.join(COMMIT_FOLDER, uid);
        Utils.writeObject(saveFile, this); // problem
    }
 }
