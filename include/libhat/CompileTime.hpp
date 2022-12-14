#pragma once

#include <algorithm>
#include <string>
#include <stdexcept>

namespace hat::detail {

    template<typename T>
    concept char_iterator = std::is_same_v<std::iter_value_t<T>, char>;
}

namespace hat {

    template<size_t N>
    struct string_literal {
        constexpr string_literal(const char (&str)[N]) {
            std::copy_n(str, N, value);
        }

        [[nodiscard]] constexpr const char* c_str() const {
            return (const char*) &this->value[0];
        }

        [[nodiscard]] constexpr const char* begin() const {
            return this->c_str();
        }

        [[nodiscard]] constexpr const char* end() const {
            return this->begin() + N;
        }

        char value[N];
    };

    template<detail::char_iterator Iter>
    static constexpr int atoi(Iter begin, Iter end, int base = 10) {
        if (base < 2 || base > 36) {
            throw std::invalid_argument("Invalid base specified");
        }

        int value = 0;
        auto digits = base < 10 ? base : 10;
        auto letters = base > 10 ? base - 10 : 0;

        for (auto iter = begin; iter != end; iter++) {
            char ch = *iter;
            value *= base;
            if (ch >= '0' && ch < '0' + digits) {
                value += (ch - '0');
            } else if (ch >= 'A' && ch < 'A' + letters) {
                value += (ch - 'A' + 10);
            } else if (ch >= 'a' && ch < 'a' + letters) {
                value += (ch - 'a' + 10);
            } else {
                // Throws an exception at runtime AND prevents constexpr evaluation
                throw std::invalid_argument("Unexpected character in integer string");
            }
        }
        return value;
    }

    static constexpr int atoi(std::string_view str, int base = 10) {
        return atoi(str.cbegin(), str.cend(), base);
    }
}