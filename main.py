from typing_extensions import Self
import numpy as np
import pandas as pd
import matplotlib as plt
import random
import json
from json import JSONEncoder

print('imports done successfully')


class point:

    def __init__(self, position: tuple) -> None:
        self.__position = position
        self.dimension = len(position)

    @property
    def position(self) -> tuple:
        return self.__position

    def __repr__(self) -> str:
        return f'{__class__.__name__}{self.position}'

    @position.setter
    def position(self, new_position: tuple) -> None:
        assert len(
            new_position) == self.dimension, f"new position must have same dimension as old position, old dimension {self.dimension}"
        new_position = [int(i) for i in new_position]
        print(f'position changed from {self.__position} to {new_position}')
        self.__position = new_position

    def distance_squared(self, other: Self) -> float:
        assert self.dimension == other.dimension, "points of different dimensions"
        return sum([(i - j)**2 for i, j in (zip(self.position, other.position))])


class PointEncoder(JSONEncoder):
    def default(self, o):
        if isinstance(o, point):
            return {"coordinates": o.position, "dimension": o.dimension, o.__class__.__name__: True}
        return JSONEncoder.default


def winner_parent(centers: list, point: point) -> point:
    min: float = -1
    winner = None
    for center in centers:
        distance = point.distance_squared(center)
        if min == -1 or min > distance:
            min = distance
            winner = center
    return winner


def main():
    # read data from file
    path = 'asuka.csv'
    pandas_csv = pd.read_csv(path)
    pandas_data = pandas_csv.iloc[:, 0: 3].copy()
    # reds, greens, blues = pandas_data.get(
        # 'r'), pandas_data.get('g'), pandas_data.get('b')
    # pandas_data.head()  # check
    # print(pandas_data)
    # print(pandas_csv)
    centers = list()
    k = 3
    dimension = len(pandas_data.columns)
    for _ in range(k):
        cords = []
        for _2 in range(dimension):
            cords.append(random.randint(0, 255))
        centers.append(point(tuple(cords)))
    center_children = {i: [] for i in centers}
    print(center_children)

    points = []
    for rgb in pandas_data.values:
        lst = []
        for i in range(dimension):
            lst.append(rgb[i])
        points.append(point(tuple(lst)))

    def average():
        for _point in points:  # time consumer
            center_children[winner_parent(
                centers, _point)].append(_point.position)

        for center in center_children:  # amazing function
            if len(center_children[center]) == 0:
                continue
            a = np.average(center_children[center], axis=0)
            center.position = a
    for _ in range(1):
        average()
    print(f'final average points: {centers}')
    with open("centers.json", 'w') as f:
        json.dump(centers, f, cls=PointEncoder, indent=4)


if __name__ == "__main__":
    main()
