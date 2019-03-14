
/**
 *
 * @author xiaoditao
 */
public class qaModel {
    // set an array to get the result of the choices
    int[] res;
    // constructor
    public qaModel() {
        res = new int[4];
    }
    // add up the number of the choices
    public void add(String answer) {
       switch (answer){
                case "A":
                    res[0]++;
                    break;
                case "B":
                    res[1]++;
                    break;
                case "C":
                    res[2]++;
                    break;
                case "D":
                    res[3]++;
                    break;
                default:
                    break;
        } 
    }
    // clear the int array in the model
    public void clearInt(){
       res = new int[4];
    }
}
