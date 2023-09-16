



def tsc0(l:list[int]) -> list[tuple[int, int, int]]:
    res = []
    for i, vi in enumerate(l):
        for j, vj in enumerate(l):
            for k, vk in enumerate(l):
                if i == j or i == k or j == k:
                    continue
                if vi + vj + vk == 0:
                    res.append((vi, vj, vk))

    return res

def tsc1(l:list[int]) -> list[tuple[int, int, int]]:
    res: list[tuple[int, int, int]] = []
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

    return res


def tsc2(l: list[int]) -> list[tuple[int, int, int]]:
    res: list[tuple[int, int, int]] = []
    cache: set[int] = set()

    for a in l:
        for b in cache:
            c: int = -(a + b)
            if c in cache:
                res.append((c, b, a))
                break
        cache.add(a)
    
    return res

def tsc3(l: list[int]) -> list[tuple[int, int, int]]:
    res: list[tuple[int, int, int]] = []
    sor: list[int] = sorted(l)
    fp: int = 0
    bp: int = len(sor) - 1

    for a in l:
        while fp < bp:
            b: int = sor[fp]
            c: int = sor[bp]
            sum: int = b + c
            if sum == 0:
                res.append((a, b, c))
                break
            elif sum < 0:
                fp += 1
            else:
                bp -= 1
        fp = 0
        bp = len(sor) - 1

    return res


if __name__ == '__main__':
    pass
