# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Sejong (세종) is a Java library for creating Korean Hangul Word Processor files (.hwpx format). It provides a structured way to build document components like styles, numbering systems, and tables.

## Building and Running

```bash
# Build the project
mvn clean package

# Run the main example (currently TableExample)
mvn exec:java

# Run tests
mvn test
```

## Architecture Goals

The project aims to provide a clean, consistent API for generating HWPX documents. We're restructuring the codebase to follow these principles:

1. **Clean Separation of Concerns**: Each component should have a single responsibility
2. **Consistent Builder Pattern**: All builders should follow the same pattern
3. **Fluent API**: Method chaining for intuitive document construction
4. **High-Level Abstractions**: Hide low-level hwpxlib details from users

## Project Structure

- **Core**: Base classes and interfaces
  - `HWPXDocument`: Main entry point for document creation
  - `DocumentBuilder`: Top-level document builder interface
  
- **Components**: Individual document components
  - `Style`: Style definitions
  - `Paragraph`: Paragraph handling
  - `Numbering`: Numbering system
  - `Table`: Table creation
  
- **Utils**: Helper classes
  - `HWPXWriter`: File output
  - `IdGenerator`: ID management

## Development Tasks

Current focus is on refactoring the codebase to:
1. Create a consistent public API
2. Hide implementation details
3. Reduce direct dependencies on hwpxlib classes
4. Improve testability

## Code Conventions

- Use builder pattern consistently
- Keep classes focused on single responsibilities
- Document public APIs with JavaDoc
- Use descriptive names for methods and parameters
- Validate inputs early
- Throw appropriate exceptions with clear messages