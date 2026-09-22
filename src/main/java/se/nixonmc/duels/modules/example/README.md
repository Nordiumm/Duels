# Example Module

This module exists as a reference for developing NixonDuels modules.

Modules should:

- Implement DuelsModule
- Receive DuelsCore through their constructor
- Use Core managers instead of creating duplicate systems
- Keep their own domain-specific state
- Clean up their state in disable()
- Support multiple instances where applicable

Core provides shared systems such as:

- Configuration
- Messages
- Tasks
- Player contexts
- Events
- Permissions
- Storage