#include <avr/io.h>
#include <util/delay.h>
#include "musicUtil.h"


/*
 Play a tone at the frequency specified for one second
 */
void play_sixteenth_note(unsigned short freq)
{
    unsigned long period;
    freq /= 4;
    period = 250000 / freq;      // Period of note in microseconds
    
    while (freq--) {
        // Make PB4 high
        PORTB |= (1 << PB4);
        // Use variable_delay_us to delay for half the period
        variable_delay_us(period/2);
        // Make PB4 low
        PORTB &= ~(1 << PB4);
        // Delay for half the period again
        variable_delay_us(period/2);
    }
}

void play_eighth_note(unsigned short freq){
    play_sixteenth_note(freq);
    play_sixteenth_note(freq);
}

void play_quater_note(unsigned short freq){
    play_eighth_note(freq);
    play_eighth_note(freq);
}
void play_half_note(unsigned short freq) {
    play_quater_note(freq);
    play_quater_note(freq);
}
void play_whole_note(unsigned short freq){
    play_half_note(freq);
    play_half_note(freq);
}

/*
 variable_delay_us - Delay a variable number of microseconds
 */
void variable_delay_us(int delay)
{
    int i = (delay + 5) / 10;
    
    while (i--)
        _delay_us(10);
}
