#include <iostream>
#include <vector>

using namespace std;

const int MAX = 1500;

vector<int> q1;
vector<int> q2;

int matrix[MAX][MAX];
int index[MAX*MAX];
bool mark[MAX*MAX];

void heapify(int);
void minimization(int, int, int);

void dijkstra(int n, int N) {

    q2[0] = matrix[0][0];
    int len = N;

    while (q1[0] != N - 1) {
        int v = q1[0];
        int w = q2[0];

        mark[v] = true;

        index[q1[len - 1]] = 0;
        --len;

        q1[0] = q1[len];
        q2[0] = q2[len];

        heapify(len);

        if (v <= N - 1 - n) {
            minimization(w, v + n, n);
        }

        if (v >= n) {
            minimization(w, v - n, n);
        }

        if ((v + 1) % n > 0) {
            minimization(w, v + 1, n);
        }

        if (v % n > 0) {
            minimization(w, v - 1, n);
        }
    }
}


int main(int argc, char* argv[]) {

    ios::sync_with_stdio(false);
    int n;
    cin >> n;

    int N = n * n;
    q1.resize(N);
    q2.resize(N);

    int spot = 0;
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            q2[spot] = 1111111;
            q1[spot] = spot;

            cin >> matrix[i][j];

            index[spot] = spot;
            mark[spot] = false;

            spot++;
        }
    }

    dijkstra(n, N);
    cout << q2[index[N - 1]];
}


void heapify(int n) {

    for (int i = 0; ; ) {
        int j = i;

        int l = 2 * i + 1;
        int r = l + 1;

        if (r < n && q2[i] > q2[r]) {
            i = r;
        }

        if (l < n && q2[i] > q2[l]) {
            i = l;
        }

        if (i == j) {
            break;
        } else {
            swap(index[q1[i]], index[q1[j]]);

            swap(q1[i], q1[j]);
            swap(q2[i], q2[j]);
        }
    }
}

void minimization(int v, int EdgeTo, int n) {

    int road = matrix[EdgeTo / n][EdgeTo % n];

    if (q2[index[EdgeTo]] > v + road && !mark[EdgeTo]) {
        q2[index[EdgeTo]] = v + road;
    }

    int parent = (index[EdgeTo] - 1) / 2;

    while (q2[index[EdgeTo]] < q2[parent] && index[EdgeTo] > 0) {
        index[q1[parent]] = index[EdgeTo];

        swap(q1[parent], q1[index[EdgeTo]]);
        swap(q2[parent], q2[index[EdgeTo]]);

        index[EdgeTo] = parent;
        parent = (index[EdgeTo] - 1) / 2;
    }
}