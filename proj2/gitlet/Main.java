package gitlet;

import java.io.File;
import java.io.IOException;

import static gitlet.Utils.*;

/** Driver class for Gitlet, a subset of the Git version-control system.
 *  @author TODO
 */
public class Main {

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND1> <OPERAND2> ... 
     */
    public static void main(String[] args) {
        // TODO: what if args is empty?
        String firstArg = args[0];
        Repository repo;
        switch(firstArg) {
            case "init":
                if (Repository.GITLET_DIR.exists()) {
                    System.out.println("A Gitlet version-control system already exists in the current directory.");
                    return;
                }
                repo = new Repository();
                break;
            case "add":
                // TODO: handle the `add [filename]` command
                if (!Repository.GITLET_DIR.exists()) {
                    System.out.println("Not in an initialized Gitlet directory.");
                    return;
                }
                if (args.length < 2) {
                    throw new GitletException("You must input valid add command.");
                }
                repo = Utils.readObject(Repository.CURRENT_REPO, Repository.class);
                repo.add(args[1]);
                break;
            // TODO: FILL THE REST IN
            case "commit":
                if (!Repository.GITLET_DIR.exists()) {
                    System.out.println("Not in an initialized Gitlet directory.");
                    return;
                }
                if (args[1].isEmpty()) {
                    System.out.println("Please enter a commit message");
                    return;
                }
                repo = Utils.readObject(Repository.CURRENT_REPO, Repository.class);
                repo.commit(args[1]);
                break;
            case "rm":
                if (!Repository.GITLET_DIR.exists()) {
                    System.out.println("Not in an initialized Gitlet directory.");
                    return;
                }
                if (args.length < 2) {
                    throw new GitletException("Please enter a valid rm command");
                }
                repo = Utils.readObject(Repository.CURRENT_REPO, Repository.class);
                repo.rm(args[1]);
                break;
            case "log":
                if (!Repository.GITLET_DIR.exists()) {
                    System.out.println("Not in an initialized Gitlet directory.");
                    return;
                }
                repo = Utils.readObject(Repository.CURRENT_REPO, Repository.class);
                repo.log();
                break;
            case "global-log":
                if (!Repository.GITLET_DIR.exists()) {
                    System.out.println("Not in an initialized Gitlet directory.");
                    return;
                }
                repo = Utils.readObject(Repository.CURRENT_REPO, Repository.class);
                repo.global_log();
                break;
            case "find":
                if (!Repository.GITLET_DIR.exists()) {
                    System.out.println("Not in an initialized Gitlet directory.");
                    return;
                }
                if (args.length < 2) {
                    throw new GitletException("Please enter a valid find command");
                }
                repo = Utils.readObject(Repository.CURRENT_REPO, Repository.class);
                repo.find(args[1]);
                break;
            case "status":
                if (!Repository.GITLET_DIR.exists()) {
                    System.out.println("Not in an initialized Gitlet directory.");
                    return;
                }
                repo = Utils.readObject(Repository.CURRENT_REPO, Repository.class);
                repo.status();
                break;
            case "checkout":
                if (!Repository.GITLET_DIR.exists()) {
                    System.out.println("Not in an initialized Gitlet directory.");
                    return;
                }
                repo = Utils.readObject(Repository.CURRENT_REPO, Repository.class);
                if (args.length == 2) {
                    repo.checkoutBranch(args[1]);
                }
                else if (args.length == 3) {
                    if (args[1].equals("--")) {
                        repo.checkoutFile(args[2]);
                    }
                    else {
                        throw new GitletException("You must input valid checkout command.");
                    }
                }
                else if(args.length == 4) {
                    repo.checkoutCommit(args[1], args[3]);
                }
                else {
                    throw new GitletException("You must input valid checkout command.");
                }
                break;
            case "branch":
                if (!Repository.GITLET_DIR.exists()) {
                    System.out.println("Not in an initialized Gitlet directory.");
                    return;
                }
                repo = Utils.readObject(Repository.CURRENT_REPO, Repository.class);
                repo.branch(args[1]);
                break;
            case "rm-branch":
                if (!Repository.GITLET_DIR.exists()) {
                    System.out.println("Not in an initialized Gitlet directory.");
                    return;
                }
                repo = Utils.readObject(Repository.CURRENT_REPO, Repository.class);
                repo.rm_branch(args[1]);
                break;
            case "reset":
                if (!Repository.GITLET_DIR.exists()) {
                    System.out.println("Not in an initialized Gitlet directory.");
                    return;
                }
                repo = Utils.readObject(Repository.CURRENT_REPO, Repository.class);
                repo.reset(args[1]);
                break;
            case "merge":
                if (!Repository.GITLET_DIR.exists()) {
                    System.out.println("Not in an initialized Gitlet directory.");
                    return;
                }
                repo = Utils.readObject(Repository.CURRENT_REPO, Repository.class);
                repo.merge(args[1]);
                break;
            default:
                System.out.println("No command with that name exists.");
                return;
        }
        repo.store();
    }
}
