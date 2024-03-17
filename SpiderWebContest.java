import java.util.*;

public class  SpiderWebContest{
    static final int N = 200005;
    static final int INF = (int)1e9;
    static Node[] seg = new Node[N * 4];
    static int n, s;

    static int chmin(int x, int val) {
        return Math.min(x, val);
    }

    static void down(int l, int r, int nodoActual) {
        int mid = (l + r) / 2;
        if (seg[nodoActual].min_dec != INF) {
            seg[nodoActual * 2].min_dec = chmin(seg[nodoActual * 2].min_dec, seg[nodoActual].min_dec);
            seg[nodoActual * 2 + 1].min_dec = chmin(seg[nodoActual * 2 + 1].min_dec, seg[nodoActual].min_dec - (mid + 1 - l));
        }
        if (seg[nodoActual].min_inc != INF) {
            seg[nodoActual * 2].min_inc = chmin(seg[nodoActual * 2].min_inc, seg[nodoActual].min_inc);
            seg[nodoActual * 2 + 1].min_inc = chmin(seg[nodoActual * 2 + 1].min_inc, seg[nodoActual].min_inc + (mid + 1 - l));
        }
        seg[nodoActual].min_dec = seg[nodoActual].min_inc = INF;
    }

    static void build(int l, int r, int nodoActual) {
        seg[nodoActual].min_dec = INF;
        seg[nodoActual].min_inc = INF;
        if (l == r) {
            seg[nodoActual].min_dec = seg[nodoActual].min_inc = Math.min(Math.abs(l - s), Math.min(Math.abs(n + l - s), Math.abs(l - n - s)));
            return;
        }
        int mid = (l + r) / 2;
        build(l, mid, nodoActual * 2);
        build(mid + 1, r, nodoActual * 2 + 1);
    }

    static void modify(int L, int R, int l, int r, int nodoActual, int v, int type) {
        if (L <= l && R >= r) {
            if (type == 1)
                seg[nodoActual].min_dec = Math.min(seg[nodoActual].min_dec, v);
            else
                seg[nodoActual].min_inc = Math.min(seg[nodoActual].min_inc, v);
            return;
        }
        down(l, r, nodoActual);
        int mid = (l + r) / 2;
        if (L <= mid) {
            modify(L, Math.min(mid, R), l, mid, nodoActual * 2, v, type);
            if (type == 1)
                v -= mid + 1 - L;
            else
                v += mid + 1 - L;
        }
        if (R > mid)
            modify(Math.max(L, mid + 1), R, mid + 1, r, nodoActual * 2 + 1, v, type);
    }

    static void smodify(int x, int l, int r, int nodoActual, int v) {
        if (l == r) {
            seg[nodoActual].min_dec = seg[nodoActual].min_inc = v;
            return;
        }
        down(l, r, nodoActual);
        int mid = (l + r) / 2;
        if (x <= mid)
            smodify(x, l, mid, nodoActual * 2, v);
        else
            smodify(x, mid + 1, r, nodoActual * 2 + 1, v);
    }

    static int query(int x, int l, int r, int nodoActual) {
        if (l == r) return Math.min(seg[nodoActual].min_dec, seg[nodoActual].min_inc);
        down(l, r, nodoActual);
        int mid = (l + r) / 2;
        if (x <= mid) return query(x, l, mid, nodoActual * 2);
        return query(x, mid + 1, r, nodoActual * 2 + 1);
    }

    public static int[] solve (int strands, int favorite, int[][] bridges){
        Scanner in = new Scanner(System.in);
        n = strands;
        int m = bridges.length;
        s = favorite;
        for(int i = 0; i <N*4;i++){
            seg[i] = new Node();
        }
        ArrayList<int[]> vec = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            int[] pair = new int[2];
            pair[0] = bridges[i][0];
            pair[1] = bridges[i][1];
            vec.add(pair);
        }
        vec.sort((a, b) -> b[0] - a[0]);

        build(1, n, 1);

        for (int[] pair : vec) {
            int hiloInicial = pair[1], hiloSiguiente = pair[1] % n + 1;
            int lval = Math.min(query(hiloInicial, 1, n, 1), query(hiloSiguiente % n + 1, 1, n, 1) + 1);
            int rval = Math.min(query(hiloSiguiente, 1, n, 1), query((hiloInicial + n - 2) % n + 1, 1, n, 1) + 1);
            smodify(hiloInicial, 1, n, 1, rval);
            smodify(hiloSiguiente, 1, n, 1, lval);

            modify(hiloInicial, n, 1, n, 1, rval, 0);
            modify(1, hiloInicial, 1, n, 1, rval + n + 1 - hiloInicial, 0);
            modify(hiloSiguiente, n, 1, n, 1, lval, 0);
            modify(1, hiloSiguiente, 1, n, 1, lval + n + 1 - hiloSiguiente, 0);

            modify(1, hiloInicial, 1, n, 1, rval + hiloInicial - 1, 1);
            modify(hiloInicial, n, 1, n, 1, rval + n, 1);
            modify(1, hiloSiguiente, 1, n, 1, lval + hiloSiguiente - 1, 1);
            modify(hiloSiguiente, n, 1, n, 1, lval + n, 1);
        }
        int[] res = new int[n];
        for (int i = 1; i <= n; ++i) {
            res[i-1] = query(i, 1, n, 1);
        }
        in.close();
        return res;
    }
}

class Node {
    public int min_dec, min_inc;
}
