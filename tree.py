import os

def print_tree(start_path, prefix=""):
    """Imprime a árvore de diretórios a partir de start_path"""
    entries = sorted(os.listdir(start_path))
    for i, entry in enumerate(entries):
        path = os.path.join(start_path, entry)
        connector = "└── " if i == len(entries) - 1 else "├── "
        print(prefix + connector + entry)
        if os.path.isdir(path):
            extension = "    " if i == len(entries) - 1 else "│   "
            print_tree(path, prefix + extension)

if __name__ == "__main__":
    # Caminho da raiz do projeto (onde está o pom.xml)
    root_dir = os.path.join(os.getcwd(), "src")
    if not os.path.exists(root_dir):
        print("A pasta 'src' não foi encontrada na raiz do projeto.")
    else:
        print("src")
        print_tree(root_dir)
