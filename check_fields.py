def check_fields(data, fields):
    if isinstance(data, dict):
        if fields.issubset(data.keys()) and all(data[field] for field in fields):

            return True
        return any(check_fields(value, fields) for value in data.values() if type(value) in (dict, list))
    elif isinstance(data, list):
        return any(check_fields(item, fields) for item in data)
    return False