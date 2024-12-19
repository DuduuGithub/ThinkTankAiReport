import argparse
from transformers import AutoTokenizer, AutoModelForMaskedLM
import torch
import os

# 解析命令行参数
parser = argparse.ArgumentParser(description='Get CLS token from text.')
parser.add_argument('input_file', type=str, help='The input file containing the text to process.')
parser.add_argument('output_file', type=str, help='The output file to save the CLS vector.')
args = parser.parse_args()

# 加载BERT模型和tokenizer
tokenizer = AutoTokenizer.from_pretrained("bert-base-chinese")
model = AutoModelForMaskedLM.from_pretrained("bert-base-chinese", output_hidden_states=True)

# 从文件读取输入文本
with open(args.input_file, 'r', encoding='utf-8') as f:
    text = f.read()  # 读取文件内容

# 分块处理长文本
max_length = 510
tokens = tokenizer.tokenize(text)  # 将文本分词
chunks = [tokens[i:i+max_length] for i in range(0, len(tokens), max_length)]

# 分别处理每个块
cls_vectors = []
for chunk in chunks:
    chunk_text = tokenizer.convert_tokens_to_string(chunk)  # 将tokens还原为字符串
    inputs = tokenizer(chunk_text, return_tensors="pt", max_length=max_length, padding="max_length", truncation=False)
    with torch.no_grad():
        outputs = model(**inputs)
    # 获取[CLS]向量
    cls_vector = outputs.hidden_states[-1][:, 0, :]  # 最后一层的[CLS]
    cls_vectors.append(cls_vector)

# 求平均
avg_cls_vector = torch.mean(torch.stack(cls_vectors), dim=0)

# 保存平均[CLS]向量到文件
with open(args.output_file, "w") as f:
    f.write(str(avg_cls_vector.tolist()))  # 将张量转换为列表后写入文件

# 清理临时文件
os.remove(args.input_file)

