package gitlet;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

import static gitlet.Utils.*;

// TODO: any imports you need here

/** Represents a gitlet repository.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author TODO
 */
public class Repository implements Serializable {
    /**
     * TODO: add instance variables here.
     * List all instance variables of the Repository class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided two examples for you.
     */

    /** The current working directory. */
    public static final File CWD = new File(System.getProperty("user.dir"));
    /** The .gitlet directory. */
    public static final File GITLET_DIR = join(CWD, ".gitlet");
    public static final File STAGE_FOLDER = join(GITLET_DIR, ".stagingArea");
    public static final File BLOB_FOLDER = join(GITLET_DIR, ".blobs");
    public static final File CURRENT_REPO = join(GITLET_DIR, ".currentRepository");
    public static final File REMOVAL_FOLDER = join(GITLET_DIR, ".removedFiles");

    /* TODO: fill in the rest of this class. */
    public static final Commit initCommit = new Commit();
    public Commit head;
    public HashMap<String, String> stageArea;
    public HashMap<String, String> rm;
//    public Commit master;
    public HashMap<String,Commit> branches;
    public String curBranch;
    public String curSha;

    public Repository()  {
        setupPersistence();
        initCommit.saveCommit();
        head = initCommit;
        stageArea = new HashMap<>();
        rm = new HashMap<>();
        Commit master = head;
        curBranch = "master";
        branches = new HashMap<>();
        branches.put(curBranch, master);
        curSha = Utils.sha1(Utils.serialize(head));
    }


    public void setupPersistence() {
        if(!GITLET_DIR.exists()) {
            GITLET_DIR.mkdir();
        }
        if (!Commit.COMMIT_FOLDER.exists()) {
            Commit.COMMIT_FOLDER.mkdir();
        }
        if (!STAGE_FOLDER.exists()) {
            STAGE_FOLDER.mkdir();
        }
        if (!BLOB_FOLDER.exists()) {
            BLOB_FOLDER.mkdir();
        }
        if (!CURRENT_REPO.exists()) {
            try {
                CURRENT_REPO.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (!REMOVAL_FOLDER.exists()) {
            REMOVAL_FOLDER.mkdir();
        }
        // todo: update other directory when we create other classes
    }
    public void init() {
        initCommit.saveCommit();
    }

    public void store() {
        Utils.writeObject(CURRENT_REPO,this);
    }
    /**
     * add function
     * 0. add the file to the blob dir
     * 1. add a file to the staging dir
     * 2. overwrite the previous file in dir
     * 3. if the cwd file is identical to the commit one, do not stage and delete the staged one
     * 4. if the file doesn't exist, throw an error
     *
     * what should I do??
     * 1. create the stage directory
     * 2. save the file to directory
     * 3. blobs save file content while staging area stores the metadata
     *
     */
    public void add(String filename) {
        // first, read file content and save to blob folder
        // read file content
        File thisFile = Utils.join(CWD, filename);
        if (!thisFile.exists()) {
            System.out.println("File does not exist.");
            return;
        }
        String fileContent = readContentsAsString(thisFile);
        // if the cwd file is identical to the commit one, do not stage and delete the staged one
        // first we should read the content
        // second, compare the two, use blobcode to find the blob of the commit, in other word, if blobcode is in the
        // blobs hashmap, do not create new blob
        // third, check if the stageArea has blobcode, if so, delete it

        // get the serialization folder name
        String blobCode = Utils.sha1(Utils.serialize(fileContent));

        // read the current commit folder, get the blobs
        HashMap<String, String> blobs= head.getBlobs();

        // check if blobcode is in the blobs
        if (blobs.containsKey(filename) && blobs.get(filename).equals(blobCode)) {
            if (stageArea.containsKey(filename)) {
                // todo: think about if we should save file in the stage_folder
//                Utils.restrictedDelete(Utils.join(STAGE_FOLDER, stageArea.get(filename)));
//                Utils.restrictedDelete(Utils.join(BLOB_FOLDER, stageArea.get(filename)));
                stageArea.remove(filename);
            }
            if (rm.containsKey(filename)) {
                rm.remove(filename);
            }
            return;
        }
        // write serialization file
        writeContents(Utils.join(BLOB_FOLDER, blobCode), fileContent);
//        writeObject(Utils.join(STAGE_FOLDER, blobCode), fileContent);
        stageArea.put(filename, blobCode);
        // todo: RM
    }

    /**
     * check if the file exists, and if so, read the content in byte[]
     */
    public String readFile(String filename) {
        File thisFile = Utils.join(CWD, filename);
        if (!thisFile.exists()) {
            throw new GitletException("File does not exist.");
        }
        String fileContent = readContentsAsString(thisFile);
        return fileContent;
    }
    public void commit(String msg) {
        // 1. create a new commit, cpy from parent
        // 2. update the stage one and rm one
        // 3. clear stage area and rm area
        // 4. update tree and change head
        ArrayList<String> headParent = head.getParent();
        ArrayList<String> newParent = new ArrayList<>();
        newParent.addAll(headParent);
        newParent.add(Utils.sha1(Utils.serialize(head)));
        HashMap<String, String> headBlobs = head.getBlobs();
        HashMap<String, String> newBlobs = new HashMap<>();
        newBlobs.putAll(headBlobs);
        // read from stage to find the correlated blobs
        if (stageArea.isEmpty() && rm.isEmpty()) {
            System.out.println("No changes added to the commit.");
            return;
        }
        for (String eachKey: stageArea.keySet()) {
            newBlobs.put(eachKey, stageArea.get(eachKey));
        }
        stageArea.clear();
        for (String eachKey: rm.keySet()) {
            newBlobs.remove(eachKey);
        }
        rm.clear();
        Commit newCommit = new Commit(msg, new Date(), newParent, newBlobs);
        newCommit.saveCommit();
        head = newCommit;
        Commit curhead = head;
        branches.put(curBranch, curhead);
        curSha = Utils.sha1(Utils.serialize(head));
        // todo: rm
    }

    // todo: rm hasn't passed the test
    public void rm(String fileName) {
        // first, get the file SHA_1, so we should read file content and serialize it
        // check if it's in the stageArea, if so, delete
        // check if it's in the head commit, if so, stage it in the rm, and delete in the current directory
        // error
//        String fileContent = readFile(fileName);
//        String sha = Utils.sha1(Utils.serialize(fileContent));
        if (stageArea.containsKey(fileName)) {
            stageArea.remove(fileName);
        }
        else if (head.getBlobs().containsKey(fileName)) {
            rm.put(fileName, head.getBlobs().get(fileName));
            Utils.restrictedDelete(Utils.join(CWD, fileName));
        }
        else {
            System.out.println("No reason to remove the file.");
        }
    }

    public void log() {
        // print the commit history, we should get the parent list and deserialize all the commit in the list
        // todo: format time, try to understand
        // todo: merge
        // todo: know the hacky code!!!!
        List<String> allCommits = Utils.plainFilenamesIn(Commit.COMMIT_FOLDER);
        String heauid = Utils.sha1(Utils.serialize(head));
        ArrayList<String> commitList = new ArrayList<>();
        for (String uid: head.getParent()) {
            commitList.add(uid);

        }
//        Commit test1 = Commit.fromFile(commitList.get(0));
//        Commit test2 = Commit.fromFile(commitList.get(1));
        commitList.add(curSha);
        for (int i = commitList.size() - 1; i >= 0; i--) {
            String uid = commitList.get(i);
            printLog(uid);
        }
    }

    public void printLog(String uid) {
        Commit curCommit = Commit.fromFile(uid);
        System.out.println("===");
        System.out.println("commit " + uid);
        Formatter fmt = new Formatter(Locale.ENGLISH);
        Date cal = curCommit.getTimeStamp();
        fmt.format("%ta %tb %td %tR:%tS %tY %tz", cal, cal, cal, cal, cal, cal, cal);
        System.out.println("Date: " + fmt);
        System.out.println(curCommit.getMessage() + "\n");
    }

    public void global_log() {
        List<String> allCommits = Utils.plainFilenamesIn(Commit.COMMIT_FOLDER);
        for (String eachUid: allCommits) {
            printLog(eachUid);
        }
    }

    public void find(String msg) {
        // todo: quotation marks
        // todo: find method hasn't passed the test
        List<String> allCommits = Utils.plainFilenamesIn(Commit.COMMIT_FOLDER);
        int count = 0;
        for (String eachUid: allCommits) {
            Commit curCommit = Commit.fromFile(eachUid);
            if (curCommit.getMessage().equals(msg)) {
                System.out.println(eachUid);
                count ++;
            }
        }
        if (count == 0) {
            System.out.println("Found no commit with that message.");
        }
    }

    public void status() {
        // print branches
        // print staged files
        // print removed files
        // Modifications Not Staged For Commit, Untracked Files
        // todo: didn't pass test
        // todo: extra credit
        // todo: check toString and toArray
        Set<String> branchSet = branches.keySet();
        Object[] branchesArray = branchSet.toArray();
        Arrays.sort(branchesArray);
        System.out.println("=== Branches ===");
        for (Object eachBranch: branchesArray) {
            if (curBranch.equals(eachBranch.toString())) {
                System.out.println("*" + eachBranch.toString());
            }
            else {
                System.out.println(eachBranch.toString());
            }
        }
        System.out.println("");


        System.out.println("=== Staged Files ===");
        if (!stageArea.isEmpty()) {
            Object[] stageArray = stageArea.keySet().toArray();
            Arrays.sort(stageArray);
            for (Object eachStage: stageArray) {
                System.out.println(eachStage.toString());
            }
        }
        System.out.println("");

        System.out.println("=== Removed Files ===");
        if (!rm.isEmpty()) {
            Object[] rmArray = rm.keySet().toArray();
            Arrays.sort(rmArray);
            for (Object eachRm: rmArray) {
                System.out.println(eachRm.toString());
            }
        }
        System.out.println("");

        System.out.println("=== Modifications Not Staged For Commit ===");
        System.out.println("");
        System.out.println("=== Untracked Files ===");
        System.out.println("");
    }

    public void checkoutFile(String filename) {
        // find the file in the current commit
        // deserialized the file content
        // write the file in cwd
        // if file does not exist, error
        HashMap<String,String> blobs = head.getBlobs();
        String sha = blobs.get(filename);
        if (sha == null) {
            System.out.println("File does not exist in that commit.");
            return;
        }
        writeToCwd(sha, filename);
    }

    /**
     * deserialize the uid blob and read the content in string
     */
    public static String readBlob(String uid) {
        File readFile = Utils.join(BLOB_FOLDER, uid);
        return Utils.readContentsAsString(readFile);
    }
    /**
     * first deserialize the uid blob and read the content in string and then write the content to cwd
     */
    public static void writeToCwd(String uid, String filename){
        String fileContent = readBlob(uid);
        File fileInCwd = Utils.join(CWD, filename);
        if (!fileInCwd.exists()) {
            try {
                fileInCwd.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        writeContents(fileInCwd, fileContent);
    }
    // todo: do we need to abbr??
    public void checkoutCommit(String commitId, String filename) {
        File readFile = Utils.join(Commit.COMMIT_FOLDER, commitId);
        if (!readFile.exists()) {
            System.out.println("No commit with that id exists.");
            return;
        }
        Commit curCommit = Utils.readObject(readFile, Commit.class);
        String sha = curCommit.getBlobs().get(filename);
        if (sha == null) {
            System.out.println("File does not exist in that commit.");
            return;
        }
        writeToCwd(sha, filename);
    }

    public void checkoutBranch(String branchname) {
        // first check if the branch name exists, if not throw an error
        // current branch, equal to head, then throw an error
        // first check if there is a file that is not tracked by the current branch, if so throw an error
        // read given branch's head commit blobs, and write all the content to the cwd
        // delete the other file
        // clear staging area
        // the given branch is seen as the current, head
        // todo: branches has not pass the test
        if (!branches.containsKey(branchname)) {
            System.out.println("No such branch exists.");
            return;
        }
        if (curBranch.equals(branchname)) {
            System.out.println("No need to checkout the current branch.");
            return;
        }
        Set<String> curFileSet = head.getBlobs().keySet();
        Set<String> newFileSet = branches.get(branchname).getBlobs().keySet();
        List<String> cwdFileList = Utils.plainFilenamesIn(CWD); // todo: hacky!!!
        for (String filename: cwdFileList) {
            if (newFileSet.contains(filename) && !curFileSet.contains(filename)) {
                System.out.println("There is an untracked file in the way; delete it, or add and commit it first.");
                return;
            }
            String sha = head.getBlobs().get(filename);
            if (!readContentsAsString(Utils.join(BLOB_FOLDER, sha)).equals(readContentsAsString(Utils.join(CWD, filename)))) {
                System.out.println("There is an untracked file in the way; delete it, or add and commit it first.");
                return;
            }
        }
        // change the cwd
        for (String filename: newFileSet) {
            writeToCwd(branches.get(branchname).getBlobs().get(filename), filename);
        }
        for (String filename: curFileSet) {
            if (!newFileSet.contains(filename)) {
                Utils.restrictedDelete(Utils.join(CWD, filename));
            }
        }
        stageArea.clear();
        rm.clear();
        curBranch = branchname;
        head = branches.get(branchname);
        curSha = Utils.sha1(Utils.serialize(head));
    }

    public void branch(String branchname) {
        if (branches.containsKey(branchname)) {
            System.out.println("A branch with that name already exists.");
            return;
        }
        Commit newBranch = head;
        branches.put(branchname, newBranch);
    }

    public void rm_branch(String branchname) {
        if (!branches.containsKey(branchname)) {
            System.out.println("A branch with that name does not exist.");
            return;
        }
        if (curBranch.equals(branchname)) {
            System.out.println("Cannot remove the current branch.");
            return;
        }
        branches.remove(branchname);
    }

    public void reset(String commitUid) {
        File commitFile = Utils.join(Commit.COMMIT_FOLDER, commitUid);
        if (!commitFile.exists()) {
            System.out.println("No commit with that id exists.");
            return;
        }
        Commit newCommit = Commit.fromFile(commitUid);
        Set<String> curFileSet = head.getBlobs().keySet();
        Set<String> newFileSet = newCommit.getBlobs().keySet();
        for (String filename: newFileSet) {
            checkoutCommit(commitUid, filename);
        }
        for (String filename: curFileSet) {
            if (!newFileSet.contains(filename)) {
                Utils.restrictedDelete(Utils.join(CWD, filename));
            }
        }
//        if (!curFileSet.containsAll(newFileSet)) {
//            System.out.println("There is an untracked file in the way; delete it, or add and commit it first.");
//            return;
//        }
//        // change the cwd
//        for (String filename: newFileSet) {
//            writeToCwd(newCommit.getBlobs().get(filename), filename);
//        }
//        curFileSet.removeAll(newFileSet);
//        for (String filename: curFileSet) {
//            Utils.restrictedDelete(Utils.join(CWD, filename));
//        }
        stageArea.clear();
        rm.clear();
        head = newCommit;
        branches.put(curBranch, head);
    }

    public void merge(String branchname) {
        // first find split point, should find parent list which has the same last one but next one
        // check, if split point == given branch, message; if split point == current branch, check out given branch
        // get all file set, the three total
        // 1.if modified in given but not head, stage given for addition
        // 2.if modified head but not other, don't change
        // 3.if both modified, in same way: don't change; in different way: conflict
        // 4.only in head, don't change
        // 5.only in given, stage given for addition
        // 6.only absent in given, stage for remove, and untracked
        // 7.only absent in current, don't change
        // commit, with message
        // conflict should print a message in the terminal

        // error checking:
        // stage additions and removals present
        // check branch name existence
        // check given branch with curBranch if the same
        // check untracked file, if it's overwritten or deleted, error
        if (!stageArea.isEmpty() || !rm.isEmpty()) {
            System.out.println("You have uncommitted changes.");
            return;
        }
        if (!branches.containsKey(branchname)) {
            System.out.println("A branch with that name does not exist.");
            return;
        }
        if (curBranch.equals(branchname)) {
            System.out.println("Cannot merge a branch with itself.");
            return;
        }
        Commit givenCommit = branches.get(branchname);
        Commit splitPoint = findSplitPoint(head, givenCommit);
        if (splitPoint.equals(givenCommit)) {
            System.out.println("Given branch is an ancestor of the current branch.");
            return;
        }
        if (splitPoint.equals(head)) {
            checkoutBranch(branchname);
            System.out.println("Current branch fast-forwarded.");
            return;
        }
        // done all the preparations and then need to merge the fileset
        Set<String> parentFileSet = splitPoint.getBlobs().keySet();
        Set<String> headFileSet = head.getBlobs().keySet();
        Set<String> givenFileSet = givenCommit.getBlobs().keySet();
        Set<String> allFile = new HashSet<>();
        allFile.addAll(parentFileSet);
        allFile.addAll(headFileSet);
        allFile.addAll(givenFileSet);
        Boolean conflict = false;
        // done the file set, should implement the 7 file rules
        for (String filename: allFile) {
            String uidofParent = splitPoint.getBlobs().get(filename);
            String uidofHead = head.getBlobs().get(filename);
            String uidofGiven = givenCommit.getBlobs().get(filename);
            // 1.if modified in given but not head, stage given for addition
            if (uidofParent.equals(uidofHead) && splitPoint.getBlobs().containsKey(filename) && givenCommit.getBlobs().containsKey(filename)) {
                stageArea.put(filename, uidofGiven);
            }
            // 3.if both modified, in same way: don't change; in different way: conflict
            if (!uidofParent.equals(uidofHead) && !uidofParent.equals(uidofGiven) && !uidofGiven.equals(uidofHead)) {
                // conflict
                // first read content and then write a new file, should stage and store the file in the BLOB_FOLDER
                File headFile = Utils.join(BLOB_FOLDER, uidofHead);
                String headFileContent = readContentsAsString(headFile);
                File givenFile = Utils.join(BLOB_FOLDER, uidofGiven);
                String givenFileContent = readContentsAsString(givenFile);
                // huanhang
                writeContents(Utils.join(STAGE_FOLDER, filename),"<<<<<<< HEAD", headFileContent, "=======", givenFileContent, ">>>>>>>");
                String fileContent = Utils.readContentsAsString(Utils.join(STAGE_FOLDER, filename));
                String blobCode = Utils.sha1(Utils.serialize(fileContent));
                writeContents(Utils.join(BLOB_FOLDER, blobCode), fileContent);
                stageArea.put(filename, blobCode);
                Utils.restrictedDelete(Utils.join(STAGE_FOLDER, filename));
                conflict = true;
            }
            // 5.only in given, stage given for addition
            if (!splitPoint.getBlobs().containsKey(filename) && !head.getBlobs().containsKey(filename) && givenCommit.getBlobs().containsKey(filename)) {
                stageArea.put(filename, uidofGiven);
            }
            // 6.only absent in given, stage for remove, and untracked
            if (splitPoint.getBlobs().containsKey(filename) && uidofHead.equals(uidofParent) && !givenCommit.getBlobs().containsKey(filename)) {
                rm.put(filename, uidofParent);
            }
        }
        // commit
        commit("Merged" + branchname + "into" + curBranch);
        if (conflict) {
            System.out.println("Encountered a merge conflict.");
        }
        // todo: error checking

    }

    public Commit findSplitPoint(Commit headCommit, Commit givenCommit) {
        List<String> headParent = head.getParent();
        List<String> givenParent = givenCommit.getParent();
        for (int i = 0; i < Math.min(headParent.size(), givenParent.size()); i++) {
            if (headParent.get(i) == givenParent.get(i)) {
                if ( i+1 == Math.min(headParent.size(), givenParent.size()) || headParent.get(i+1) != givenParent.get(i+1)) {
                    return Commit.fromFile(headParent.get(i));
                }
            }
        }
        return null;
    }

}
