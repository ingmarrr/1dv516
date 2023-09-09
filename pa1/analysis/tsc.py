
def tsc0(l:list[int]) -> list[tuple[int, int, int]]:
    res = []
    for i, vi in enumerate(l):
        for j, vj in enumerate(l):
            for k, vk in enumerate(l):
                if i == j or i == k or j == k:
                    continue
                if vi + vj + vk == 0:
                    res.append((vi, vj, vk))

    print(res)
    return res

def tsc1(l:list[int]) -> list[tuple[int, int, int]]:
    res: list[(int, int, int)] = []
    cache: set[int] = set()

    for ax, a in enumerate(l):
        cache.clear()

        for ix, i in enumerate(l):
            if ix == ax:
                continue
            j = -(a + i)
            if j in cache:
                res.append((a, j, i))
            cache.add(i)

    print(res)
    return res


def tsc2(l: list[int]) -> list[tuple[int, int, int]]:
    rest: list[tuple[int, int, int]] = []
    cache: set[int] = set()

    for a in l:
        for b in cache:
            c: int = -(a + b)
            if c in cache:
                rest.append((c, b, a))
                break
        cache.add(a)
    
    print(rest)
    return rest


if __name__ == '__main__':
    l = [6, 3, -1, 1, 4, -3, 0, -4, 6, -6, 7, -3, -2, -8, 0, 1, -9, -10, -10, 8, 0, -2, -5, -10, -4, 6, -9, 8, 7, 3, 0, -2, 6, 10, 6, 10, -7, 2, 1, 3, 9, -7, -7, -2, 2, -6, 5, 10, 10, 4, 6, 4, -5, -6, -7, 6, 5, 7, 3, -9, -2, 2, -2, 1, -9, -4, -7, -7, -5, 10, 2, 8, -7, 3, -1, 9, -1, -5, 7, -8, 6, -4, 10, 10, 10, 3, -9, -10, 4, -8, -5, -5, 3, 5, -6, -7, -1, 9, 1, 5]
    print(tsc2(l))