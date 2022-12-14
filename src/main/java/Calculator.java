import java.util.*; 
public class Calculator {
    
    Stack<String> opStack;
    Stack<Integer> valStack;
    ArrayList<String> array;

    // Constructor create two stacks
    public Calculator(){
        Stack<String> opStack = new Stack<>();
        Stack<Integer> valStack = new Stack<>();
        this.opStack = opStack;
        this.valStack = valStack;
    }
    
 // returns string output of solved input
    public String equate(String input){
        try {
    	int result = evaluateInfix(input);
        String resultString = "" + result;
        return resultString; 
        } catch(java.lang.Exception e) {return "Invalid input please try again";}

    }
    
    // Convert string to arrayList
    public void toArray(String input){
    	this.array = new ArrayList<String>();
        String tmp = "";
        for(int i = 0; i <= input.length()-1; i++){
            // if op add operator to list
            if(isOperator(input.charAt(i)) && input.charAt(i)!=' '){
                tmp += input.charAt(i);
                array.add(tmp);
                tmp = "";
            }
            // if number add number to list
            else if(input.charAt(i)!=' '){
                while(i<input.length() && !isOperator(input.charAt(i)) && !isSpace(input.charAt(i))){
                    tmp += input.charAt(i);
                    i++;
                }
                if(i<input.length() && isOperator(input.charAt(i))) {
                	i--; 
                }
                array.add(tmp);
                tmp = "";
            }
        }
    }
    // Checks if passed String is a boolean
    public Boolean isOperator(String op){
        if(op.equalsIgnoreCase("+") || op.equalsIgnoreCase("-") || op.equalsIgnoreCase("*") || op.equalsIgnoreCase("/") || op.equalsIgnoreCase("(") || op.equalsIgnoreCase(")")){
            return true;
        }
        else return false;
    }

    // Checks if passed char is a boolean
    public Boolean isOperator(char op){
        if(op == '+' || op == '-' || op == '*' || op == '/' || op =='(' || op ==')'){
            return true;
        }
        else return false;
    }
    
 // Checks if passed char is a space
    public Boolean isSpace(char op){
        if(op == ' '){
            return true;
        }
        else return false;
    }

    public void pushToStacks(String op){
        try{
        // Check if operator
        if(op.equalsIgnoreCase("+") || op.equalsIgnoreCase("-") || op.equalsIgnoreCase("*") || op.equalsIgnoreCase("/") || op.equalsIgnoreCase("(") || op.equalsIgnoreCase(")")){
            opStack.push(op);
        }
        // Else push to valStack
        else{
            valStack.push(Integer.parseInt(op));
        }
        } catch (java.lang.Exception e){System.out.println("Error: Unable to push to satck");}
    }

    // Evaluates infix expression by converting to postfix
    public Integer evaluateInfix(String infix){
        toArray(infix);
        String output = "";
        String value;
        String tmp;
        for(int i=0; i<=array.size()-1; i++){
            value = array.get(i);
            // If "(" push onto operator stack
            if(value.equalsIgnoreCase("(")){
                pushToStacks(value);
            }
            // If ")" pop off items until "("
            else if(value.equalsIgnoreCase(")")){
                while(!opStack.peek().equalsIgnoreCase("(") || opStack.isEmpty()){
                    tmp = opStack.pop();
                    output += tmp;
                }
                opStack.pop();
            }
            // If operator run check
            else if(isOperator(value)){
                // If first value add to stack
                if(opStack.empty()){
                    opStack.push(value);
                }
                // If operator has greater prescedence than top of stack pop off top of stack and push operator
                else{
                    String top = opStack.peek();
                    while(!opStack.empty() && opPrecedenceGreaterOrEqual(top, value)){
                        output += opStack.pop() + " ";
                        if(!opStack.empty()) {
                        	top = opStack.peek();
                        }
                    }
                    opStack.push(value);
                }
            }
            // If operand add to string
            else{
                output += value + " ";
            }
        }
        while(!opStack.isEmpty()) {
        	tmp = "";
        	tmp = opStack.pop();
        	output += tmp + " ";
        }
        // System.out.println("Testing output:" + output);
        return evaluatePostfix(output);
        
    }

    // Assigns value to operator and returns if it is greater or equal to another
    Boolean opPrecedenceGreaterOrEqual(String op1, String op2){
        int op1Val = 0;
        int op2Val = 0;

        switch(op1){
            case "*":
                op1Val = 2;
                break;
            case "/":
                op1Val = 2;
                break;
            case "+":
                op1Val =1;
                break;
            case "-":
                op1Val =1;
                break;
        }

        switch(op2){
            case "*":
                op2Val = 2;
                break;
            case "/":
                op2Val = 2;
                break;
            case "+":
                op2Val =1;
                break;
            case "-":
                op2Val =1;
                break;
        }
        if(op1Val >= op2Val && op1Val !=0 && op2Val !=0){
            return true;
        }
        else return false;
    }

    // Evaluates postfix expression
    public Integer evaluatePostfix(String postfix){
        toArray(postfix);
        int result;
        int operand1;
        int operand2;

        for(int i=0; i<=array.size()-1; i++){
            String value = array.get(i);
            if(!isOperator(value)){
                int dvalue = Integer.parseInt(value);
                valStack.push(dvalue);
            }
            if(isOperator(value)){
                switch(value){
                    case "+":
                        operand2 = valStack.pop();
                        operand1 = valStack.pop();
                        result = operand1 + operand2;
                        valStack.push(result);
                        break;
                    case "-":
                        operand2 = valStack.pop();
                        operand1 = valStack.pop();
                        result = operand1 - operand2;
                        valStack.push(result);
                        break;

                    case "*":
                        operand2 = valStack.pop();
                        operand1 = valStack.pop();
                        result = operand1 * operand2;
                        valStack.push(result);
                        break;

                    case "/":
                        operand2 = valStack.pop();
                        operand1 = valStack.pop();
                        result = operand1 / operand2;
                        valStack.push(result);
                        break;
                    default:
                        System.out.println("Invalid operator recieved");
                }
            }
        }

        return valStack.pop();
    }
}
