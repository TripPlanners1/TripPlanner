import pandas as pd
import numpy as np
import glob

extension = 'csv'
all_filenames = [i for i in glob.glob('../../data/raw/*.{}'.format(extension))]

combined_data = pd.concat([pd.read_csv(f) for f in all_filenames])

# export to csv

if len(glob.glob('combined_data.csv')) < 1:
    combined_data.to_csv("combined_data.csv", index=False, encoding='utf-8-sig')

df = pd.read_csv('combined_data.csv')

df.drop(columns=['Unnamed: 8'], axis=1, inplace=True)

# Missing values
print(df.isnull().any())

df.dropna(inplace=True)

# Checking null values
print(df.isnull().any())

export_path = '../../data/cleaned/combined_data.csv'
if len(glob.glob(export_path)) < 1:
    df.to_csv(export_path, index=True, encoding='utf-8-sig')
