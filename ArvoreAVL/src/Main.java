import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        //Arvore binaria AVL (Adelson-Velsky e Landis)
        //Uma arvore que se manter balanceada automaticamente

        Scanner sc = new Scanner(System.in);
        Node<Integer> root = null;
        System.out.print("Deseja inserir? (1 - Sim / 2 - Não): ");
        int opc = sc.nextInt();

        while (opc == 1){
            System.out.print("Informe o valor: ");
            int value = sc.nextInt();
            root = insert(root, value);
            System.out.print("Deseja inserir? (1 - Sim / 2 - Não): ");
            opc = sc.nextInt();
        }

        inOrder(root);
        System.out.println("Value height tree: " +  root.getHeight());

        System.out.print("Deseja removar um valor da arvore? (1 - Sim / 2 - Não): ");
        opc = sc.nextInt();
        if (opc == 1){
            System.out.print("Informe o valor: ");
            int value = sc.nextInt();
            root = remove(root, value);
        }

        inOrder(root);

        sc.close();
    }

    public static Node<Integer> insert(Node<Integer> no, int value){
        if (no == null){
            no = new Node<>();
            no.setValue(value);
            no.setHeight(0);
            return no;
        }

        if (value <= no.getValue()){
            no.setLeft(insert(no.getLeft(), value));
        }
        else {
            no.setRight(insert(no.getRight(), value));
        }

        no.setHeight(Math.max(altura(no.getLeft()), altura(no.getRight())) + 1);

        return verificarRotacao(fatorBalanceamento(no), no);
    }

    private static Node<Integer> verificarRotacao(int fatorBalanceamento, Node<Integer> no) {
        if (fatorBalanceamento > 1){
            if (altura(no.getLeft()) == -1){
                return rotacaoEsquerdaDireita(no);
            }
            return rotacaoDireita(no);
        }
        if (fatorBalanceamento < -1) {
            if (altura(no.getRight()) == 1){
               return rotacaoDireitaEsquerda(no);
            }
            return rotacaoEsquerda(no);
        }
        return no;
    }

    private static Node<Integer> rotacaoDireita(Node<Integer> no) {
        Node<Integer> j, k;

        j = no.getLeft();
        k = j.getRight();

        j.setRight(no);
        no.setLeft(k);

        no.setHeight(Math.max(altura(no.getLeft()), altura(no.getRight()) + 1));
        j.setHeight(Math.max(altura(j.getLeft()), altura(j.getRight()) + 1));

        return j;
    }

    private static Node<Integer> rotacaoEsquerda(Node<Integer> no) {
        Node<Integer> j, k;

        j = no.getRight();
        k = no.getLeft();

        j.setLeft(no);
        no.setRight(k);

        no.setHeight(Math.max(altura(no.getLeft()), altura(no.getRight()) + 1));
        j.setHeight(Math.max(altura(j.getLeft()), altura(j.getRight()) + 1));

        return j;
    }

    private static Node<Integer> rotacaoDireitaEsquerda(Node<Integer> no) {
        Node<Integer> j, k;

        k = no.getRight();
        j = k.getLeft();

        no.setRight(j);
        j.setRight(k);

        k.setHeight(Math.max(altura(k.getLeft()), altura(k.getRight()) + 1));
        j.setHeight(Math.max(altura(j.getLeft()), altura(j.getRight()) + 1));
        no.setHeight(Math.max(altura(no.getLeft()), altura(no.getRight()) + 1));

        return rotacaoEsquerda(no);
    }

    private static Node<Integer> rotacaoEsquerdaDireita(Node<Integer> no) {
        Node<Integer> j, k;

        k = no.getLeft();
        j = k.getRight();

        no.setLeft(j);
        j.setLeft(k);

        k.setHeight(Math.max(altura(k.getLeft()), altura(k.getRight()) + 1));
        j.setHeight(Math.max(altura(j.getLeft()), altura(j.getRight()) + 1));
        no.setHeight(Math.max(altura(no.getLeft()), altura(no.getRight()) + 1));

        return rotacaoDireita(no);
    }

    private static int fatorBalanceamento(Node<Integer> no) {
        if (no != null){
            return altura(no.getLeft()) - altura(no.getRight());
        }
        return 0;
    }

    public static int altura(Node<Integer> no){
        if (no == null) return -1;
        return no.getHeight();
    }

    public static void inOrder(Node<Integer> no){
        if (no == null) return;
        inOrder(no.getLeft());
        System.out.println(no.getValue());
        inOrder(no.getRight());
    }

    private static Node<Integer> remove(Node<Integer> no, int value) {
        if (no == null) return null;

        if (no.getValue().equals(value)){
            if (no.getLeft() == null && no.getRight() == null){
                return null;
            }
            if (no.getLeft() != null && no.getRight() == null){
                return no.getLeft();
            }
            if (no.getLeft() == null && no.getRight() != null){
                return no.getRight();
            }
            if (no.getLeft() != null && no.getRight() != null){
                Node<Integer> j, k, i = null;
                j = no.getRight();
                k = j.getLeft();

                if (k == null){
                    j.setLeft(no.getLeft());
                }
                else {
                    while (k != null) {
                        i = k;
                        k = k.getLeft();
                    }
                    i.setLeft(no.getLeft());
                }
                return j;
            }
        }

        if (value <= no.getValue()){
            no.setLeft(remove(no.getLeft(), value));
        }else {
            no.setRight(remove(no.getRight(), value));
        }

        no.setHeight(Math.max(altura(no.getLeft()), altura(no.getRight()) + 1));

        return verificarRotacao(fatorBalanceamento(no), no);
    }
}