# overflow-synth
Development of a musical noise synthesizer based on modular arithmetic.

Every wave has two states: Up and down, and every state lasts half its wave period.

It is analized which wave is nearer to a change in its state and the synth obtains how long it should wait for that. If the time is smaller than the limit that time is waited. On the other hand if it is bigger, that time gets compressed at only 'time mod limit'.

The user can modify waves period, volume and waveform between four basic forms: Square, Sine, Triangle and Sawtooth.

The limit can be also modified, aswell as total volume and two delays.

Project made in java with netbeans 11
