import pandas as pd
import pyarrow, fsspec, huggingface_hub
# Login using e.g. `huggingface-cli login` to access this dataset
df = pd.read_parquet("hf://datasets/Rif-SQL/time-series-uk-retail-supermarket-price-data/base_retail_gb_snappy.parquet")
df.to_csv("data/data.csv", index=False)