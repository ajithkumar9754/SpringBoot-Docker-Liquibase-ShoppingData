import pandas as pd

def readCSVData():
    import pandas as pd
    pd.set_option('display.max_rows', None)
    df = pd.read_csv('original_data.csv')

    filterByColorDF = df[df['Product properties'].str.contains("color")]
    filterByColorDF['Product properties'] = filterByColorDF['Product properties'].apply(processColorProperty)


    filterByGBLimitDF = df[df['Product properties'].str.contains("gb_limit")]
    filterByGBLimitDF['Product properties'] = filterByGBLimitDF['Product properties'].apply(processGBProperty)

    frames = [filterByColorDF, filterByGBLimitDF]
    result = pd.concat(frames)

    result.set_index('Product type', inplace=True)

    print(result)

    result.to_csv('data-processed.csv')


def processColorProperty(color):
    token= color.split(':')
    return token[1]

def processGBProperty(gb):
    token = gb.split(':')
    return token[1]

data = readCSVData()

## very small work around is needed on this generated CSV file to sync with Liquibase
